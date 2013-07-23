package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Gets the connection to IMAP-server and opens & closes folders.
 */
public class IMAPserver {

    IMAPFolder folder = null;
    Store store = null;
    String subject = null;
    Flags.Flag flag = null;
    private HashMap<String, IMAPFolder> folders;
    private String IMAPadress = "imap.googlemail.com";
    private String username = "okkopa.2013@gmail.com";
    private String password = "ohtu2013okkopa";

    /**
     * Formats offline settings ready for login.
     *
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public IMAPserver() throws NoSuchProviderException, MessagingException {
        settingsForIMAPSSL();

        folders = new HashMap<String, IMAPFolder>();
    }

    private void settingsForIMAPSSL() throws NoSuchProviderException, MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
    }

    private void openIfNotOpen(IMAPFolder folder) throws MessagingException {
        if (!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
        }
    }

    /**
     * Logins into given IMAP-server with username and password.
     *
     * @throws MessagingException
     */
    public void login() throws MessagingException {
        store.connect(IMAPadress, username, password);
    }

    /**
     * Opens desired folder and gives its node back. If used before, gives the same node as last time.
     *
     * @param emailBox Which mailbox we wanted to have open and used.
     * @return node to folder.
     * @throws MessagingException
     */
    public IMAPFolder selectAndGetFolder(String emailBox) throws MessagingException {
        if (folders.containsKey(emailBox)) {
            folder = folders.get(emailBox);
        } else {
            folder = (IMAPFolder) store.getFolder(emailBox);
            folders.put(emailBox, folder);
        }

        openIfNotOpen(folder);

        return folder;
    }

    /**
     * Closes folders and IMAP-server connection.
     *
     * @throws MessagingException
     */
    public void close() throws MessagingException {
        for (Entry<String, IMAPFolder> folderItem : folders.entrySet()) {
            folder = folderItem.getValue();
            
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
        }
        if (store != null) {
            store.close();
        }
    }
}
