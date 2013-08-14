package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
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
    private String text;
    private String attachmentName;

    private EmailSenderImpl() {
    }

    /**
     * Initializes the object.
     *
     * @param settings Settings to use for sending email.
     */
    @Autowired
    public EmailSenderImpl(Settings settings) {
        this.properties = settings;
        this.sender = properties.getProperty("mail.message.replyto");
        this.subject = properties.getProperty("mail.message.topic");
        this.text = properties.getProperty("mail.message.body");
        this.attachmentName = properties.getProperty("mail.message.attachmentname");
        if (!attachmentName.endsWith(".pdf")) {
            attachmentName += ".pdf";
        }
    }

    @Override
    public void send(String receiverEmailAddress, InputStream attachment) throws MessagingException {
        OKKoPaMessage msg = new OKKoPaMessage(receiverEmailAddress, sender, properties);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            msg.addPDFAttachment(attachment, attachmentName);
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
        msg.send();
    }
}
