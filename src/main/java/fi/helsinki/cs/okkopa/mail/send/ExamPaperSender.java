/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import javax.mail.MessagingException;

/**
 *
 * @author anttkaik
 */
public interface ExamPaperSender {

    
    public void send(ExamPaper examPaper) throws MessagingException;
    
    
}
