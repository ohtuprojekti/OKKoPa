package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.FailedEmailDatabase;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.mail.send.EmailSender;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FailedEmail;
import fi.helsinki.cs.okkopa.model.Student;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetryFailedEmailsStage extends Stage {

    private static Logger LOGGER = Logger.getLogger(RetryFailedEmailsStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private EmailSender emailSender;
    private String saveRetryFolder;
    private Saver fileSaver;
    private FailedEmailDatabase failedEmailDatabase;
    private int retryExpirationMinutes;

    @Autowired
    public RetryFailedEmailsStage(EmailSender emailSender, ExceptionLogger exceptionLogger,
            Settings settings, Saver fileSaver, FailedEmailDatabase failedEmailDatabase) {
        this.emailSender = emailSender;
        this.exceptionLogger = exceptionLogger;
        saveRetryFolder = settings.getProperty("mail.send.retrysavefolder");
        this.fileSaver = fileSaver;
        this.failedEmailDatabase = failedEmailDatabase;
        retryExpirationMinutes = Integer.parseInt(settings.getProperty("mail.send.retryexpirationminutes"));
    }

    @Override
    public void process(Object in) {
        retryFailedEmails();
        processNextStages(null);
    }

    private void sendEmail(FailedEmail failedEmail, InputStream attachment) {
        try {
            LOGGER.debug("Lähetetään sähköposti.");
            emailSender.send(failedEmail.getReceiverEmail(), attachment);
        } catch (MessagingException ex) {
            LOGGER.debug("Sähköpostin lähetys epäonnistui.");
            exceptionLogger.logException(ex);
        }
    }

    private void retryFailedEmails() {
        // Get failed email send attachments (PDF-files)
        LOGGER.debug("Yritetään lähettää sähköposteja uudelleen.");
        ArrayList<File> fileList = fileSaver.list(saveRetryFolder);
        if (fileList == null) {
            LOGGER.debug("Ei uudelleenlähetettävää.");
            return;
        };
        List<FailedEmail> failedEmails;
        try {
            failedEmails = failedEmailDatabase.listAll();
        } catch (SQLException ex) {
            exceptionLogger.logException(ex);
            return;
        }
        for (FailedEmail failedEmail : failedEmails) {
            for (File pdf : fileList) {
                if (failedEmail.getFilename().equals(pdf.getName())) {
                    try {
                        FileInputStream fis = new FileInputStream(pdf);
                        sendEmail(failedEmail, fis);
                        IOUtils.closeQuietly(fis);
                        // TODO epäonnistuneet!
                    } catch (Exception ex) {
                        exceptionLogger.logException(ex);
                        continue;
                    }
                }
            }
        }
        // TODO clean nonmatching database <-> folder
    }
}
