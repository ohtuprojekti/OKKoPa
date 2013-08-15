package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

/**
 * Returns attachment, subject and received time from the message.
 */
public class IMAPmessage {

    private Message message;
    private ArrayList<InputStream> attachments;
    private String contentType;

    /**
     * Formats this object to use.
     *
     * @param message What message we want to examine.
     */
    public IMAPmessage(Message message) {
        this.message = message;
    }

    /**
     * Return messages subject.
     *
     * @return Messages subject.
     */
    public String getSubject() {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            return null;
        }
    }

    /**
     * Returns all messages attachments as HashMap, where key is filename and
     * value is InputStream.
     *
     * @return HashMam of attachments.
     * @throws IOException
     * @throws MessagingException
     */
    public ArrayList<InputStream> getAttachments() throws IOException, MessagingException {
        contentType = this.message.getContentType();

        attachments = new ArrayList<>();

        if (contentType.contains("multipart")) {
            // content may contain attachments
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);

                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    attachments.add(part.getInputStream());
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

    /**
     * Returns messages received time.
     *
     * @return message received time.
     * @throws MessagingException
     */
    public Date getTime() throws MessagingException {
        return this.message.getReceivedDate();
    }

    public Message getIMAPMessage() {
        return message;
    }
}
