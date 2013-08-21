package fi.helsinki.cs.okkopa.mail.send;

import java.io.InputStream;
import javax.mail.MessagingException;

public interface EmailSender {

    void send(String receiverEmailAddress, InputStream attachment) throws MessagingException;
}
