package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

public class IMAPmessage {

    private Message message;
    private HashMap<String, InputStream> attachments;
    private String contentType;

    public IMAPmessage(Message message) {
        this.message = message;
    }

    public String getSubject() {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            return null;
        }
    }

    public HashMap<String, InputStream> getAttachments() throws IOException, MessagingException {
        contentType = this.message.getContentType();

        attachments = new HashMap<String, InputStream>();

        // store attachment file name, separated by comma
        String attachFiles = "";

        if (contentType.contains("multipart")) {
            // content may contain attachments
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    attachments.put(part.getFileName(), part.getInputStream());
                }
            }

            if (attachments.isEmpty()) {
                return null;
            }
        } else {
            return null;
        }
        return attachments;
    }
}
