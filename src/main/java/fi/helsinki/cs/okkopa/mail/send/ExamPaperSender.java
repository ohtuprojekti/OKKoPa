package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import javax.mail.MessagingException;

/**
 * Send an exam paper
 */

public interface ExamPaperSender {

    /**
     * Sends an exam paper
     * @param examPaper Exam paper to send
     * @throws MessagingException if sending the message fails.
     */
    public void send(ExamPaper examPaper) throws MessagingException;
    
    
}
