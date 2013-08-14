package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.FailedEmailDatabase;
import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.EmailSender;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FailedEmail;
import fi.helsinki.cs.okkopa.model.Student;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    private EmailSender emailSender;
    private String saveRetryFolder;
    private Saver fileSaver;
    private FailedEmailDatabase failedEmailDatabase;

    @Autowired
    public SendEmailStage(EmailSender emailSender, ExceptionLogger exceptionLogger,
            Settings settings, Saver fileSaver, FailedEmailDatabase failedEmailDatabase) {
        this.emailSender = emailSender;
        this.exceptionLogger = exceptionLogger;
        saveRetryFolder = settings.getProperty("mail.send.retrysavefolder");
        this.fileSaver = fileSaver;
        this.failedEmailDatabase = failedEmailDatabase;
        //        retryExpirationMinutes = Integer.parseInt(settings.getSettings().getProperty("mail.send.retryexpirationminutes"));
    }

    @Override
    public void process(ExamPaper examPaper) {
        sendEmail(examPaper);
        processNextStages(examPaper);
    }

    private void sendEmail(ExamPaper examPaper) {
        try {
            LOGGER.debug("Lähetetään sähköposti.");
            InputStream is = new ByteArrayInputStream(examPaper.getPdf());
            emailSender.send(examPaper.getStudent().getEmail(), is);
            IOUtils.closeQuietly(is);
        } catch (MessagingException ex) {
            LOGGER.debug("Sähköpostin lähetys epäonnistui.");
            saveFailedEmail(examPaper);
            LOGGER.debug("Tallennetaan PDF-liite levylle.");
            exceptionLogger.logException(ex);
        }
    }

    private void saveFailedEmail(ExamPaper examPaper) {
        try {
            String filename = System.currentTimeMillis() + ".pdf";
            InputStream is = new ByteArrayInputStream(examPaper.getPdf());
            fileSaver.saveInputStream(is, saveRetryFolder, filename);
            IOUtils.closeQuietly(is);
            FailedEmail failedEmail = new FailedEmail();
            failedEmail.setFilename(filename);
            failedEmail.setReceiverEmail(examPaper.getStudent().getEmail());
            failedEmail.setFailTime(new Date());
            failedEmailDatabase.addFailedEmail(failedEmail);
        } catch (FileAlreadyExistsException | SQLException ex1) {
            exceptionLogger.logException(ex1);
            // TODO if one fails what then?
        }
    }
}
