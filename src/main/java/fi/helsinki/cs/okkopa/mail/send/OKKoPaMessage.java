package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author anttkaik
 */
public class OKKoPaMessage {
    
    Multipart body;
    Properties properties;
    private static final String MAIL_PROPERTY = "mail.smtp.host";
    private static final int VIESTI_INDEX = 0;
    
    String subject;
    String receiver;
    String sender;
    
    
    public OKKoPaMessage(String receiver, String sender, Properties properties) throws MessagingException {
        // Add parameter properties
        this.properties = properties;
        this.receiver = receiver;
        this.sender = sender;
        
        body = new MimeMultipart();
        
        BodyPart viesti = new MimeBodyPart();
        body.addBodyPart(viesti, VIESTI_INDEX);
    }
    
    
    public OKKoPaMessage(String receiver, String sender) throws MessagingException {
        this(receiver, sender, System.getProperties());
    }
    
    
    public void setText(String text) throws MessagingException {
        BodyPart viesti = body.getBodyPart(VIESTI_INDEX);
        viesti.setText(text);
    }
    
    public void setSubject(String subject) throws MessagingException {
        this.subject = subject;
    }
    
    
    protected Session generateSession() {
        return Session.getInstance(properties);
        
    }
    
    
    public final MimeMessage generateMessage() throws MessagingException {
        // Get the default Session object.
        Session session = generateSession();
        
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        
        // Set To: header field of the header.
        message.addRecipient(javax.mail.Message.RecipientType.TO,
                                 new InternetAddress(receiver));
        
        message.setSubject(subject);
        message.setContent(body);
        return message;  
    }
    
    
    public void send() throws MessagingException {
        MimeMessage message = generateMessage();
        
        Transport.send(message);
    }
    
    public void addAttachment(String fileName) throws MessagingException {
        BodyPart bodypart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileName);
        bodypart.setDataHandler(new DataHandler(source));
        bodypart.setFileName(fileName);
        body.addBodyPart(bodypart);
    }
    
    
    //testiä
    public static void main(String[] args) throws MessagingException {
        Properties props = Settings.SMTPPROPS;
        OKKoPaMessage msg = new OKKoPaMessage("okkopa.2013@gmail.com", "vaaralahettaja@gmail.com");
        msg.setText("toimiikoä");
        msg.setText("yksi viesti");
        msg.setSubject("testi123");
        msg.addAttachment("liite.txt");
        msg.send();
    }
    
}
