package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import javax.mail.MessagingException;

public interface ExamPaperSender {

    
    public void send(ExamPaper examPaper) throws MessagingException;
    
    
}
