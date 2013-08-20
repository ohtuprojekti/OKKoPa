package fi.helsinki.cs.okkopa.mail.read;

import fi.helsinki.cs.okkopa.main.Settings;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailReadImpl implements EmailRead {

    private Store store;
    private Folder inbox;
    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private final String inboxFolderName;
    private final boolean deleteAfterProcessing;
    private final String processedFolderName;

    @Autowired
    public EmailReadImpl(Settings settings) {
        host = settings.getProperty("mail.imap.host");
        username = settings.getProperty("mail.imap.user");
        password = settings.getProperty("mail.imap.password");
        port = Integer.parseInt(settings.getProperty("mail.imap.port"));
        inboxFolderName = settings.getProperty("mail.imap.inboxfoldername");
        deleteAfterProcessing = Boolean.parseBoolean(settings.getProperty("mail.imap.deleteafterprocessing"));
        processedFolderName = settings.getProperty("mail.imap.processedfolder");
    }

    @Override
    public void close() throws MessagingException {
        inbox.close(false);
        store.close();
    }

    @Override
    public void connect() throws NoSuchProviderException, MessagingException {
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties);
        store = session.getStore("imaps");
        store.connect(host, port, username, password);
        inbox = store.getFolder(inboxFolderName);
        inbox.open(Folder.READ_WRITE);
    }

    @Override
    public List<InputStream> getMessagesAttachments(Message message) throws MessagingException, IOException {
        List<InputStream> attachments = new ArrayList<>();
        Multipart multipart = (Multipart) message.getContent();
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
                    && !StringUtils.isNotBlank(bodyPart.getFileName())) {
                // attachments only
                continue;
            }
            InputStream inputStream = bodyPart.getInputStream();
            attachments.add(inputStream);
        }
        return attachments;
    }

    @Override
    public Message getNextMessage() throws MessagingException {
        Message[] messages = inbox.getMessages();
        if (messages.length == 0) {
            return null;
        }
        return messages[0];
    }

    @Override
    public void cleanUpMessage(Message message) throws MessagingException {
        // if not deleting, move to processed folder
        if (!deleteAfterProcessing) {
            Folder processed = store.getFolder(processedFolderName);
            processed.open(Folder.READ_WRITE);
            Message[] messages = {message};
            inbox.copyMessages(messages, processed);
        }
        // delete in any case from inbox
        message.setFlag(Flags.Flag.DELETED, true);
        inbox.expunge();
    }

    @Override
    public void closeQuietly() {
        try {
            close();
        } catch (MessagingException ex) {
            // Ignore
        }
    }
}
