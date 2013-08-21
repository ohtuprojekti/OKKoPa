package fi.helsinki.cs.okkopa.mail.send;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;

/**
 * Easily send emails.
 *
 */
public class OKKoPaMessage {

    private Multipart body;
    private Properties properties;
    private static final int VIESTI_INDEX = 0;
    private static final String PDF_MIME_NAME = "application/pdf";
    private String subject;
    private String receiver;
    private String sender;
    private Session session;

    /**
     * Creates new instance from receiver, sender and mailproperties.
     *
     * @param receiver Receiver email address
     * @param sender Sender email address
     * @param properties Properties
     * @throws MessagingException
     */
    public OKKoPaMessage(String receiver, String sender, Properties properties) throws MessagingException {
        // Add parameter properties
        this.properties = properties;
        this.receiver = receiver;
        this.sender = sender;

        body = new MimeMultipart();

        BodyPart viesti = new MimeBodyPart();
        body.addBodyPart(viesti, VIESTI_INDEX);
    }

    /**
     * Creates new instance using System.getproperties.
     *
     * @param receiver Receiver email address
     * @param sender Sender email address
     * @throws MessagingException
     */
    public OKKoPaMessage(String receiver, String sender) throws MessagingException {
        this(receiver, sender, System.getProperties());
    }

    /**
     * Sets the text of the message Replaces any previously set texts
     *
     * @param text
     * @throws MessagingException
     */
    public void setText(String text) throws MessagingException {
        BodyPart viesti = body.getBodyPart(VIESTI_INDEX);
        viesti.setText(text);
    }

    /**
     * Sets new subject to the message
     *
     * @param subject New subject for the message
     * @throws MessagingException
     */
    public void setSubject(String subject) throws MessagingException {
        this.subject = subject;
    }

    /**
     * Generates session from properties This method can be overridden to
     * provide authentication.
     *
     * @return Generated session
     */
    protected Session generateSession() {
        if (properties.getProperty("mail.smtp.auth").equals("true")) {
            return Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                            properties.getProperty("mail.smtp.password"));
                }
            });
        } else {
            return Session.getInstance(properties);
        }
    }

    /**
     * Builds the message. Typically used before sending.
     *
     * @return Built message
     * @throws MessagingException
     */
    private MimeMessage generateMessage() throws MessagingException {
        // Get the default Session object.
        session = generateSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));

        // Set To: header field of the header.
        message.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(receiver));

        message.setSubject(subject);
        message.setContent(body);
        return message;
    }

    /**
     * Sends the message.
     *
     * @throws MessagingException
     */
    public void send() throws MessagingException {
        MimeMessage message = generateMessage();
        Transport.send(message);
    }

    /**
     * Helper method to avoid copy-paste.
     *
     * @param source File/InputStream/URL
     * @param name Name of the file
     * @throws MessagingException
     */
    private void addAttachment(DataSource source, String name) throws MessagingException {
        BodyPart bodypart = new MimeBodyPart();
        bodypart.setDataHandler(new DataHandler(source));
        bodypart.setFileName(name);
        body.addBodyPart(bodypart);
    }

    /**
     * Adds an attachment from local filesystem.
     *
     * @param fileName Path of the file.
     * @throws MessagingException
     */
    public void addAttachment(String fileName) throws MessagingException {
        DataSource source = new FileDataSource(fileName);
        addAttachment(source, fileName);
    }

    /**
     * Adds an attachment from specified inputstream.
     *
     * @param is Inputstream
     * @param mimeType
     * @see https://en.wikipedia.org/wiki/Internet_media_type
     * @param name Name of the attachment
     * @throws IOException
     * @throws MessagingException
     */
    public void addAttachment(InputStream is, String mimeType, String name) throws IOException, MessagingException {
        DataSource source = new ByteArrayDataSource(is, mimeType);
        addAttachment(source, name);
    }

    /**
     * Adds a pdf attachment.
     *
     * @param is Inputstream of the pdf
     * @param name Name of the pdf. Must end with .pdf
     * @throws IOException
     * @throws MessagingException
     */
    public void addPDFAttachment(InputStream is, String name) throws IOException, MessagingException {
        addAttachment(is, PDF_MIME_NAME, name);
    }

    public Multipart getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }
}
