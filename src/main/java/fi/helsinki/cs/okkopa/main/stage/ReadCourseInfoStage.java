package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.BatchDetailDAO;
import fi.helsinki.cs.okkopa.database.OkkopaDatabaseConnectionSource;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.main.BatchDetails;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.BatchDbModel;
import fi.helsinki.cs.okkopa.model.CourseInfo;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCourseInfoStage extends Stage<List<ExamPaper>, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(ReadCourseInfoStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private PDFProcessor pdfProcessor;
    private BatchDetails batch;

    @Autowired
    public ReadCourseInfoStage(PDFProcessor pDFProcessor, ExceptionLogger exceptionLogger, BatchDetails batch) {
        this.pdfProcessor = pDFProcessor;
        this.exceptionLogger = exceptionLogger;
        this.batch = batch;

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
        } catch (PdfException | NotFoundException  | SQLException | IOException ex) {
            exceptionLogger.logException(ex);
        }

        // Process all examPapers
        while (!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            // Add course info (doesn't matter if null)
            processNextStages(examPaper);
        }
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
        String[] fields = examPaper.getQRCodeString().split(":");
        LOGGER.debug("Kurssi-info luettu: " + examPaper.getQRCodeString());
        batch.setCourseCode(fields[0]);
        batch.setPeriod(fields[1]);
        batch.setYear(Integer.parseInt(fields[2]));
        batch.setType(fields[3]);
        batch.setCourseNumber(Integer.parseInt(fields[4]));

        if (fields.length >= 5) {
            BatchDetailDAO batchDao = new BatchDetailDAO(new OkkopaDatabaseConnectionSource(new Settings("settings.xml")));

            BatchDbModel bdm;
            bdm = batchDao.getBatchDetails(fields[5]);

            batch.setDefaultEmailContent(bdm.getEmailContent());
            batch.setReportEmailAddress(bdm.getReportEmailAddress());
        }

    }
}
