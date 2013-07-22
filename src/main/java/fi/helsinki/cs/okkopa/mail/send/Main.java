/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Main {
    
    private static void addAttachment(Multipart body, String filename) throws MessagingException
    {
        BodyPart bodypart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        bodypart.setDataHandler(new DataHandler(source));
        bodypart.setFileName(filename);
        body.addBodyPart(bodypart);
    } 
    
    
    
   public static void main(String [] args)
   {    
      // Recipient's email ID needs to be mentioned.
      String to = "okkopa.2013@gmail.com";

      // Sender's email ID needs to be mentioned
      String from = "lahetystesti@gmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(javax.mail.Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("otsikko");

         // Now set the actual message
         message.setText("viesti");

         //Liitteet
         Multipart body = new MimeMultipart();
         message.setContent(body);
         addAttachment(body, "liite.txt");
         
         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}