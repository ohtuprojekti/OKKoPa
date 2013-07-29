package fi.helsinki.cs.okkopa.mail.read;

import fi.helsinki.cs.okkopa.Settings;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class MailRead implements EmailRead {

    private IMAPserver server;
    private IMAPfolder IMAPfolder;
    private String toBox = "processed";
    private String IMAPadress = Settings.IMAPPROPS.getProperty("mail.imap.host");
    private String username = Settings.IMAPPROPS.getProperty("mail.imap.user");
    private String password = Settings.PWDPROPS.getProperty("imapPassword");
    private int port = Integer.parseInt(Settings.IMAPPROPS.getProperty("mail.imap.port"));
    private int howManyDaysOldAreToBeDeleted = Integer.parseInt(Settings.IMAPPROPS.getProperty("mail.imap.messages.keep.days"));
    private IMAPmessage IMAPmessage;
    private ArrayList<InputStream> attachments;
    private IMAPdelete delete;

    public MailRead() {
    }

    @Override
    public void connect() throws NoSuchProviderException, MessagingException {
        server = new IMAPserver(IMAPadress, username, password, port);
        server.login();

        IMAPfolder = new IMAPfolder(server, "inbox");
    }

    @Override
    public void close() {
        try {
            server.close();
        } catch (MessagingException ex) {
            System.out.println("!?!?!?!?!? Something unexpected happened ?!?!?!");
        }
    }

    @Override
    public ArrayList<InputStream> getNextAttachment() throws MessagingException, IOException {
        do {
            IMAPmessage = IMAPfolder.getNextmessage(toBox);

            if (IMAPmessage != null) {
                attachments = IMAPmessage.getAttachments();
            } else {
                attachments = null;
            }
            
            if (attachments != null) {
                return attachments;
            }

        } while (IMAPmessage != null);

        return null;
    }
    
    public void deleteOldMessages() throws MessagingException {
        delete = new IMAPdelete(server);
        delete.deleteOldMessages(howManyDaysOldAreToBeDeleted);
    }
}
