package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class IMAPserver {

    IMAPFolder folder = null;
    Store store = null;
    String subject = null;
    Flags.Flag flag = null;
    private String emailBox = "inbox";
    
    private String IMAPadress = "imap.googlemail.com";
    private String username = "okkopa.2013@gmail.com";
    private String password = "password";

    public IMAPserver() throws NoSuchProviderException, MessagingException {
        settingsForIMAPSSL();
        loginAndSelectFolder();
    }
    
    private void settingsForIMAPSSL() throws NoSuchProviderException, MessagingException {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
     
            Session session = Session.getDefaultInstance(props, null);
     
            store = session.getStore("imaps");
        }
     
        private void openIfNotOpen() throws MessagingException {
            if (!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
            }
        }

    private void loginAndSelectFolder() throws MessagingException {
        store.connect(IMAPadress, username, password);
 
        folder = (IMAPFolder) store.getFolder(emailBox); // This works for both email account
        
        openIfNotOpen();
    }
}
