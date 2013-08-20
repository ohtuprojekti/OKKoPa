package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.main.BatchDetails;
import fi.helsinki.cs.okkopa.main.Settings;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Send an exampaper.
 */
@Component
public class EmailSenderImpl implements EmailSender {

    private Properties properties;
    private String sender;
    private String subject;
    private String attachmentName;
    private BatchDetails batch;

    private EmailSenderImpl() {
    }

    /**
     * Initializes the object.
     *
     * @param settings Settings to use for sending email.
     */
    @Autowired
    public EmailSenderImpl(Settings settings, BatchDetails batch) {
        this.properties = settings;
        this.sender = properties.getProperty("mail.message.replyto");
        this.subject = properties.getProperty("mail.message.defaulttopic");
        this.batch = batch;
        this.attachmentName = properties.getProperty("exampaper.attachmentname");
        if (!attachmentName.endsWith(".pdf")) {
            attachmentName += ".pdf";
        }
    }

    @Override
    public void send(String receiverEmailAddress, InputStream attachment) throws MessagingException {
        OKKoPaMessage msg = new OKKoPaMessage(receiverEmailAddress, sender, properties);
        msg.setSubject(subject);
        String text = this.batch.getEmailContent();
        msg.setText(text);
        
        
        try {
            msg.addPDFAttachment(attachment, attachmentName);
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
        msg.send();
    }
}
