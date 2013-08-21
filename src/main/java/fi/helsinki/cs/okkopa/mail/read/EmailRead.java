package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Email get and attachment reading utilities.
 */
public interface EmailRead {

    /**
     * Closes the connection.
     * 
     * @throws MessagingException If there were problems with the server.
     * @throws NullPointerException If connect() was not called successfully before close().
     */
    void close() throws MessagingException;
    
    /**
     * Closes the connection without Exceptions. To be used in finally() block.
     */
    void closeQuietly();

    /**
     * Connects to the email server.
     *
     * @throws MessagingException If there were problems connecting.
     */
    void connect() throws MessagingException;
    
    /**
     * Returns next Message object or null if there are no new Messages.
     * 
     * @return Next Message object or null if there are no new Messages
     * @throws MessagingException If there were problems with the imap connection.
     */
    Message getNextMessage() throws MessagingException;

    /**
     * Returns list of given Messages attachments as a list.
     *
     * @param message Message to get the attachments from.
     * @return A list of attachments or an empty list if there are none.
     * @throws MessagingException If there are problems with the imap connection.
     * @throws IOException If there are problems reading the attachments.
     */
    List<InputStream> getMessagesAttachments(Message message) throws MessagingException, IOException;
    
    /**
     * Depending on the settings either deletes the mail from inbox or moves it to another folder.
     * 
     * @param message Message to be deleted or moved.
     * @throws MessagingException If there were problems with the connection.
     */
    void cleanUpMessage(Message message) throws MessagingException;
}
