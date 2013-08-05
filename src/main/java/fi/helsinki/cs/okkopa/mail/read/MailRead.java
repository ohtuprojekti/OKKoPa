package fi.helsinki.cs.okkopa.mail.read;

import fi.helsinki.cs.okkopa.Settings;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MailRead implements EmailRead {

    private IMAPserver server;
    private IMAPfolder IMAPfolder;
    private String toBox = "processed";
    private String IMAPaddress;
    private String username;
    private String password;
    private int port;
    private int ProcessedHowManyDaysOldAreToBeDeleted;
    private IMAPmessage IMAPmessage;
    private ArrayList<InputStream> attachments;
    private IMAPdelete delete;
    private String processedFolderToEmpty;

    @Autowired
    public MailRead(Settings settings) {
        IMAPaddress = settings.getSettings().getProperty("mail.imap.host");
        username = settings.getSettings().getProperty("mail.imap.user");
        password = settings.getSettings().getProperty("mail.imap.password");
        port = Integer.parseInt(settings.getSettings().getProperty("mail.imap.port"));
        ProcessedHowManyDaysOldAreToBeDeleted = Integer.parseInt(settings.getSettings().getProperty("mail.imap.processed.keepdays"));
        processedFolderToEmpty = settings.getSettings().getProperty("mail.imap.processed.name");
    }

    @Override
    public void connect() throws NoSuchProviderException, MessagingException {
        server = new IMAPserver(IMAPaddress, username, password, port);
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
    public ArrayList<InputStream> getNextMessagesAttachments() throws MessagingException, IOException {
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
    
    @Override
    public void deleteOldMessages() throws MessagingException {
        delete = new IMAPdelete(server);
        delete.deleteOldMessages(ProcessedHowManyDaysOldAreToBeDeleted, processedFolderToEmpty);
    }
}
