package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    ArrayList<InputStream> getNextAttachment() throws MessagingException, IOException;
    
}
