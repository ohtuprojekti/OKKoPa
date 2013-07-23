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
public class OKKoPaAuthenticatedMessage {
    
    MimeMessage message;
    Multipart body;
    Properties properties;
    javax.mail.Session session;
    private static final String MAIL_PROPERTY = "mail.smtp.host";
    private static final int VIESTI_INDEX = 0;
    
    public OKKoPaAuthenticatedMessage(String receiver, String sender, Properties properties, final String username, final String password) throws MessagingException {
        // Add parameter properties
        this.properties = properties;

        
        // Get the default Session object.
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
        });
       
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
    
    
    public OKKoPaAuthenticatedMessage(String receiver, String sender, String username, String password) throws MessagingException {
        this(receiver, sender, System.getProperties(), username, password);
    }
    

    public void setAuthentication(String username, String password) {
        PasswordAuthentication pw = new PasswordAuthentication(username, password);
        session.setPasswordAuthentication(new URLName(session.getProperty(MAIL_PROPERTY)), pw);
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
        Properties props = new Properties();//System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        OKKoPaAuthenticatedMessage msg = new OKKoPaAuthenticatedMessage("okkopa.2013@gmail.com", "vaaralahettaja@gmail.com", props, "okkopa.2013@gmail.com", "ohtu2013okkopa");
        //msg.setAuthentication("okkopa.2013@gmail.com", "ohtu2013okkopa");
        msg.setText("toimiikoä");
        msg.setText("yksi viesti");
        msg.setSubject("testi123");
        msg.addAttachment("liite.txt");
        msg.send();
    }
    
}
