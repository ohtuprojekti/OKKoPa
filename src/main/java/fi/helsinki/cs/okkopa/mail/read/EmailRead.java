package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public interface EmailRead {

    /**
     * Closes the connection.
     */
    void close();

    /**
     * Connects into the email server.
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    void connect() throws NoSuchProviderException, MessagingException;

    /**
     * Returns list of next email's attachments as list.
     *
     * @return null, if no new messages with attachments.
     * @throws MessagingException
     * @throws IOException
     */
    List<InputStream> getNextMessagesAttachments() throws MessagingException, IOException;

    /**
     * Deletes old messages from processed folder. How old messages are
     * specified in settings file.
     *
     * @throws MessagingException
     */
    void deleteOldMessages() throws MessagingException;
}
