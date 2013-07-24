/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

/**
 *
 * @author tatutall
 */
public interface EmailRead {

    void close();

    void connect() throws NoSuchProviderException, MessagingException;

    ArrayList<InputStream> getNextAttachment() throws MessagingException, IOException;
    
}
