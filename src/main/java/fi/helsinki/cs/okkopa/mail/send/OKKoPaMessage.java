/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author anttkaik
 */
public class OKKoPaMessage {
    
    MimeMessage message;
    Multipart body;
    Properties properties;
    javax.mail.Session session;
    private static final String MAIL_PROPERTY = "mail.smtp.host";
    private static final int VIESTI_INDEX = 0;
    
    public OKKoPaMessage(String receiver, String sender, Properties properties) throws MessagingException {
        // Get system properties
        this.properties = System.getProperties();
        
        // Add parameter properties
        this.properties.putAll(properties);

        
        
        // Get the default Session object.
        session = Session.getDefaultInstance(properties);
       
        
        message = new MimeMessage(session);
        
        body = new MimeMultipart();
        
        BodyPart viesti = new MimeBodyPart();
        body.addBodyPart(viesti, VIESTI_INDEX);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(sender));
        
        // Set To: header field of the header.
        message.addRecipient(javax.mail.Message.RecipientType.TO,
                                 new InternetAddress(receiver));
    }
    
    
    public OKKoPaMessage(String receiver, String sender) throws MessagingException {
        this(receiver, sender, System.getProperties());
    }
    

    public void setAuthentication(final String username, final String password, URLName host) {
        PasswordAuthentication pw = new PasswordAuthentication(username, password);
        session.setPasswordAuthentication(host, pw);
    }
    
    public void setText(String text) throws MessagingException {
        BodyPart viesti = body.getBodyPart(VIESTI_INDEX);
        viesti.setText(text);
    }
    
    public void setSubject(String subject) throws MessagingException {
        message.setSubject(subject);
    }
    
    public void send() throws MessagingException {
        message.setContent(body);
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
        OKKoPaMessage msg = new OKKoPaMessage("okkopa.2013@gmail.com", "lahetystesti2@gmail.com");
        msg.setText("toimiikoä");
        msg.setText("yksi viesti");
        msg.setSubject("testi123");
        msg.addAttachment("liite.txt");
        msg.send();
    }
    
}
