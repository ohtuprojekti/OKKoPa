package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.BatchDetailDAO;
import fi.helsinki.cs.okkopa.database.OkkopaDatabaseConnectionSource;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.mail.send.EmailSender;
import fi.helsinki.cs.okkopa.mail.send.EmailSenderImpl;
import fi.helsinki.cs.okkopa.main.BatchDetails;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.BatchDbModel;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCourseInfoStage extends Stage<List<ExamPaper>, ExamPaper> {

    private static final Logger LOGGER = Logger.getLogger(ReadCourseInfoStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private PDFProcessor pdfProcessor;
    private BatchDetails batch;
    private EmailSender emailSender;
    private Settings settings;

    @Autowired
    public ReadCourseInfoStage(PDFProcessor pDFProcessor, ExceptionLogger exceptionLogger, BatchDetails batch, Settings settings, EmailSender emailSender) {
        this.pdfProcessor = pDFProcessor;
        this.exceptionLogger = exceptionLogger;
        this.batch = batch;
        this.settings = settings;
        this.emailSender = emailSender;
    }

    @Override
    public void process(List<ExamPaper> examPapers) {
        ExamPaper courseInfoPage = examPapers.get(0);

        batch.reset();

//        CourseInfo courseInfo = null;
        try {
            courseInfoPage.setPageImages(pdfProcessor.getPageImages(courseInfoPage));
            courseInfoPage.setQRCodeString(pdfProcessor.readQRCode(courseInfoPage));
            setBatchDetails(courseInfoPage);

            // Remove if succesful so that the page won't be processed as
            // a normal exam paper.
            examPapers.remove(0);
            LOGGER.debug("Kurssi-info luettu onnistuneesti.");
        } catch (PdfException | NotFoundException | SQLException | IOException ex) {
            LOGGER.debug("jotain meni pieleen");
            exceptionLogger.logException(ex);
        }

        // Process all examPapers
        while (!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            // Add course info (doesn't matter if null)
            processNextStages(examPaper);
        }

        sendEmail();
    }

//    public CourseInfo getCourseInfo(ExamPaper examPaper) throws NotFoundException {
//        String[] fields = examPaper.getQRCodeString().split(":");
//        LOGGER.debug("Kurssi-info luettu: " + examPaper.getQRCodeString());
//        try {
//            return new CourseInfo(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3], Integer.parseInt(fields[4]));
//        } catch (Exception e) {
//            throw new NotFoundException();
//        }
//    }
    public void setBatchDetails(ExamPaper examPaper) throws SQLException, FileNotFoundException, IOException, NotFoundException {
        String[] fields = (examPaper.getQRCodeString() + ":236").split(":");
        LOGGER.debug("Kurssi-info luettu: " + examPaper.getQRCodeString());

        try {
            batch.setCourseCode(fields[0]);
            batch.setPeriod(fields[1]);
            batch.setYear(Integer.parseInt(fields[2]));
            batch.setType(fields[3]);
            batch.setCourseNumber(Integer.parseInt(fields[4]));
        } catch (Exception e) {
            throw new NotFoundException();
        }
        if (fields.length >= 6) {
            BatchDetailDAO batchDao = new BatchDetailDAO(new OkkopaDatabaseConnectionSource(new Settings("settings.xml")));
            batchDao.addBatchDetails(new BatchDbModel("236", "Viesti kannasta", "okkopa.2013@gmail.com"));
            BatchDbModel bdm;

            bdm = batchDao.getBatchDetails(fields[5]);

            batch.setEmailContent(bdm.getEmailContent());
            batch.setReportEmailAddress(bdm.getReportEmailAddress());
        }

    }

    private void sendEmail() {
        try {
            LOGGER.debug("Lähetetään sähköposti.");
            emailSender.send(batch.getReportEmailAddress(), "subject settareista", "katenoi sisältö kasaan " + batch.getTotalPages() + "/" + batch.getFailedScans(), "okkopa.", null);
        } catch (MessagingException ex) {
            LOGGER.debug("Raporttisähköpostin lähetys epäonnistui.");
            exceptionLogger.logException(ex);
        }
    }
}
