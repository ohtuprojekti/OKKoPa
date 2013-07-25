package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import java.util.*;
import javax.mail.*;

/**
 *  http://www.icegreen.com/greenmail/feedback.html
 * 
 * 
 * 
 *"Hi, I just wanted to thank you for offering GreenMail to the world. 
 * I just finished plugging it into our application (replacing Dumbster) and 
 * found it a breeze to use. I was a bit thrown by the requirement to set up 
 * a user before using the IMAP server to avoid authentication exceptions. 
 * It makes sense but can be a little confusing given the server is so permissive 
 * otherwise. It might be an interesting feature to create an account automatically 
 * if a user attempts to login but I guess this could be problematic if you support 
 * negative testing for user logins. We're using Spring so I hooked it into our 
 * application context so now whenever we start the application we have a virtual 
 * IMAP/SMTP server we can use for testing (aside from our unit testing where we 
 * are also using it) from the UI. Regards, Rob"

 *"thanks for your mock mail server; we had tried Dumbster but had a few problems with it and yours works much better."
 * 
 * 
 * 
 * 
 * @author anttkaik
 */
public class OKKoPaAuthenticatedMessage extends OKKoPaMessage {
    
    private static final String MAIL_PROPERTY = "mail.smtp.host";
    private static final int VIESTI_INDEX = 0;
    private final String username;
    private final String password;
    
    public OKKoPaAuthenticatedMessage(String receiver, String sender, Properties properties, final String username, final String password) throws MessagingException {
        super(receiver, sender, properties);
        this.username = username;
        this.password = password;
    }
    
    
    public OKKoPaAuthenticatedMessage(String receiver, String sender, String username, String password) throws MessagingException {
        this(receiver, sender, System.getProperties(), username, password);
    }
    
    
    
    @Override
    public Session generateSession() {
        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
        });
    }
    
    
    //testiä
    public static void main(String[] args) throws MessagingException {
        Properties props = Settings.SMTPPROPS;
        Properties passwords = Settings.PWDPROPS;
        if (props == null) {
            System.out.println("props null");
            return;
        }
        if (passwords == null) {
            System.out.println("passwords null");
            return;
        }
        OKKoPaAuthenticatedMessage msg = new OKKoPaAuthenticatedMessage("okkopa.2013@gmail.com", "vaaralahettaja@gmail.com", 
                props, props.getProperty("mail.smtp.user"), passwords.getProperty("smtpPassword"));
        msg.setText("toimiikoä");
        msg.setText("yksi viesti");
        msg.setSubject("testi123");
        //msg.addAttachment("liite.txt");
        msg.send();
    }
    
}
