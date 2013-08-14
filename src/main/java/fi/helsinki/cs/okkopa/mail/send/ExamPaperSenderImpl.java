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
public class ExamPaperSenderImpl implements ExamPaperSender {

    
    private Properties properties;
    private String sender;
    private String subject;
    private String text;
    private String attachmentName;
    
    
    private ExamPaperSenderImpl() {
        
    }
    
    /**
     * Initializes the object. 
     * @param settings Settings to use for sending email.
     */
    @Autowired
    public ExamPaperSenderImpl(Settings settings) {
        this.properties = settings.getSettings();
        this.sender = properties.getProperty("mail.message.replyto");
        this.subject = properties.getProperty("mail.message.topic");
        this.text = properties.getProperty("mail.message.body");
        this.attachmentName = properties.getProperty("mail.message.attachmentname");
        if (!attachmentName.endsWith(".pdf")) {
            attachmentName += ".pdf";
        }
    }    
    
    /**
     * @param examPaper
     * @throws MessagingException 
     */
    @Override
    public void send(ExamPaper examPaper) throws MessagingException {
        OKKoPaMessage msg = new OKKoPaMessage(examPaper.getStudent().getEmail(), sender, properties);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            InputStream is = new ByteArrayInputStream(examPaper.getPdf());
            msg.addPDFAttachment(is, attachmentName);
            IOUtils.closeQuietly(is);
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
        msg.send();
    }
    
}
