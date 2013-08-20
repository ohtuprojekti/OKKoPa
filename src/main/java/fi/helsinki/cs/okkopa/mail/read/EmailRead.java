package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public interface EmailRead {

    /**
     * Closes the connection.
     */
    void close() throws MessagingException;
    
    void closeQuietly();

    /**
     * Connects into the email server.
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    void connect() throws MessagingException;
    
    Message getNextMessage() throws MessagingException;

    /**
     * Returns list of next email's attachments as list.
     *
     * @return null, if no new messages with attachments.
     * @throws MessagingException
     * @throws IOException
     */
    List<InputStream> getMessagesAttachments(Message message) throws MessagingException, IOException;
    
    void cleanUpMessage(Message message) throws MessagingException;
}
