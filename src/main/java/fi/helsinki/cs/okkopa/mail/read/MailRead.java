package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class MailRead implements EmailRead {

    private IMAPserver server;
    private IMAPfolder IMAPfolder;
    private String toBox = "processed";
    private Message message;
    private String IMAPadress = "imap.googlemail.com";
    private String username = "okkopa.2013@gmail.com";
    private String password = "okkopa2013";
    private IMAPmessage IMAPmessage;

    public MailRead() {
    }

    @Override
    public void connect() throws NoSuchProviderException, MessagingException {
        server = new IMAPserver(IMAPadress, username, password);
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
        IMAPmessage = IMAPfolder.getNextmessage(toBox);
        
        return IMAPmessage.getAttachments();
    }
}
