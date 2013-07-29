/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.qr.ExamPaper;
import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author anttkaik
 */
@Component
public class ExamPaperSenderImpl implements ExamPaperSender {

    Settings settings;
    
    @Autowired
    public ExamPaperSenderImpl(Settings settings) {
        this.settings = settings;
    }    
    
    private String getReceiver(ExamPaper examPaper) {
        return examPaper.getQRCodeString();
    }
    
    @Override
    public void send(ExamPaper examPaper) throws MessagingException {
        OKKoPaMessage msg = new OKKoPaMessage(getReceiver(examPaper), "OKKoPa@cs.helsinki.fi", settings.getSettings());
        msg.setSubject("");
        msg.setText("");
        try {
            msg.addPDFAttachment(examPaper.getPdfStream(), "liite.pdf");
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
        msg.send();
    }
    
}
