/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamPaperSenderImpl implements ExamPaperSender {

    private Properties properties;
    private String sender;
    private String subject;
    private String text;
    private String attachmentName;
    
    
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
    
    @Override
    public void send(ExamPaper examPaper) throws MessagingException {
        OKKoPaMessage msg = new OKKoPaMessage(examPaper.getStudent().getEmail(), sender, properties);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            msg.addPDFAttachment(examPaper.getPdf(), attachmentName);
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
        msg.send();
    }
    
}
