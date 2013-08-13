package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FailedEmail;
import fi.helsinki.cs.okkopa.model.Student;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendEmailStage extends Stage<ExamPaper, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(SendEmailStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private ExamPaperSender examPaperSender;

    @Autowired
    public SendEmailStage(ExamPaperSender examPaperSender, ExceptionLogger exceptionLogger) {
        this.examPaperSender = examPaperSender;
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(ExamPaper in) {
        sendEmail(in, true);
        processNextStages(in);
    }

    private void sendEmail(ExamPaper examPaper, boolean saveOnError) {
        try {
            LOGGER.debug("Lähetetään sähköposti.");
            examPaperSender.send(examPaper);
        } catch (MessagingException ex) {
            LOGGER.debug("Sähköpostin lähetys epäonnistui.");
            if (saveOnError) {
                saveFailedEmail(examPaper);
                LOGGER.debug("Tallennetaan PDF-liite levylle.");
            }
            exceptionLogger.logException(ex);
        }
    }

    private void saveFailedEmail(ExamPaper examPaper) {
//        try {
//            String filename = "" + System.currentTimeMillis() + ".pdf";
//            saver.saveInputStream(examPaper.getPdf(), saveRetryFolder, filename);
//            failedEmailDatabase.addFailedEmail(examPaper.getStudent().getEmail(), filename);
//        } catch (FileAlreadyExistsException | SQLException ex1) {
//            logException(ex1);
//            // TODO if one fails what then?
//        }
    }

    private void retryFailedEmails() {
//        // Get failed email send attachments (PDF-files)
//        LOGGER.debug("Yritetään lähettää sähköposteja uudelleen.");
//        ArrayList<File> fileList = saver.list(saveRetryFolder);
//        if (fileList == null) {
//            LOGGER.debug("Ei uudelleenlähetettävää.");
//            return;
//        };
//        List<FailedEmail> failedEmails;
//        try {
//            failedEmails = failedEmailDatabase.listAll();
//        } catch (SQLException ex) {
//            logException(ex);
//            return;
//        }
//        for (FailedEmail failedEmail : failedEmails) {
//            for (File pdf : fileList) {
//                if (failedEmail.getFilename().equals(pdf.getName())) {
//                    try {
//                        FileInputStream fis = new FileInputStream(pdf);
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        IOUtils.copy(fis, bos);
//                        ExamPaper examPaper = new ExamPaper();
//                        examPaper.setPdf(bos.toByteArray());
//                        examPaper.setStudent(new Student());
//                        examPaper.getStudent().setEmail(failedEmail.getReceiverEmail());
//                        sendEmail(examPaper, false);
//                        IOUtils.closeQuietly(fis);
//                        // TODO expiration time and so on
//                    } catch (Exception ex) {
//                        logException(ex);
//                        continue;
//                    }
//                }
//            }
//        }
    }
}
