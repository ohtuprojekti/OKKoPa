/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.qr.ExamPaper;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author anttkaik
 */
public class ExamPaperSenderImpl implements ExamPaperSender {

    public ExamPaperSenderImpl() {
    }
    
    
    private String getReceiver(ExamPaper examPaper) {
        return examPaper.getQRCodeString();
    }
    
    @Override
    public void send(ExamPaper examPaper) throws MessagingException {
        Properties props = Settings.SMTPPROPS;
        OKKoPaMessage msg = new OKKoPaMessage(getReceiver(examPaper), "OKKoPa@cs.helsinki.fi", props);
        msg.setSubject("");
        msg.setText("");
        try {
            msg.addPDFAttachment(examPaper.getPdfStream(), "liite.pdf");
        } catch (IOException ex) {
            throw new MessagingException("Error while reading pdf stream");
        }
    }
    
}
