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
    private String IMAPadress;
    private String username;
    private String password;
    private IMAPFolder newFolder;

    /**
     * Formats offline settings ready for login.
     *
     *
     * @param IMAPaddress
     * @param username
     * @param password
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public IMAPserver(String IMAPaddress, String username, String password) throws NoSuchProviderException, MessagingException {
        this(IMAPaddress, username, password, -1);
    }

    /**
     * Formats offline settings ready for login.
     * 
     * @param IMAPaddress
     * @param username
     * @param password
     * @param IMAPport
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public IMAPserver(String IMAPaddress, String username, String password, int IMAPport) throws NoSuchProviderException, MessagingException {
        this.IMAPadress = IMAPaddress;
        this.username = username;
        this.password = password;

        settingsForIMAPSSL(IMAPport);

        folders = new HashMap<>();
    }

    private void settingsForIMAPSSL(int IMAPport) throws NoSuchProviderException, MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        if (IMAPport >= 0) {
            props.setProperty("mail.imaps.port", Integer.toString(IMAPport));
        }
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
     * Opens desired folder and gives its node back. If used before, gives the
     * same node as last time.
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
            this.closeFolder(folderItem.getValue());
        }
        if (store != null) {
            store.close();
        }
    }

    /**
     * Closes specified folder.
     *
     * @param folder What to close.
     * @throws MessagingException
     */
    public void closeFolder(IMAPFolder folder) throws MessagingException {
        this.folder = folder;

        if (this.folder != null && this.folder.isOpen()) {
            this.folder.close(true);
        }
    }

    public void createFolder(String newFolder) throws MessagingException {
        this.newFolder = (IMAPFolder) store.getFolder(newFolder);
        if (!this.newFolder.exists()) {
            this.newFolder.create(Folder.HOLDS_MESSAGES);
        }
    }
}
