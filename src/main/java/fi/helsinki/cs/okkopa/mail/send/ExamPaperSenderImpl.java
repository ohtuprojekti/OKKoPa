/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamPaperSenderImpl implements ExamPaperSender {

    Properties properties;
    
    @Autowired
    public ExamPaperSenderImpl(Settings settings) {
        this.properties = settings.getSettings();
    }    
    
    private String getReceiver(ExamPaper examPaper) {
        return "okkopa.2013@gmail.com";//examPaper.getEmail();
    }
    
    @Override
    public void send(ExamPaper examPaper) throws MessagingException {
        String sender = properties.getProperty("mail.message.replyto");
        String subject = properties.getProperty("mail.message.topic");
        String text = properties.getProperty("mail.message.body");
        String attachmentName = properties.getProperty("mail.message.attachmentname");
        if (!attachmentName.endsWith(".pdf")) {
            attachmentName += ".pdf";
        }
        OKKoPaMessage msg = new OKKoPaMessage(getReceiver(examPaper), sender, properties);
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
