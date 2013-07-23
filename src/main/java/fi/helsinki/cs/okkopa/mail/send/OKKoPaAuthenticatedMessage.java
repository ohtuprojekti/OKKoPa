package fi.helsinki.cs.okkopa.mail.send;

import fi.helsinki.cs.okkopa.Settings;
import java.util.*;
import javax.mail.*;

/**
 *
 * @author anttkaik
 */
public class OKKoPaAuthenticatedMessage extends OKKoPaMessage {
    
    private static final String MAIL_PROPERTY = "mail.smtp.host";
    private static final int VIESTI_INDEX = 0;
    private String username;
    private String password;
    
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
        OKKoPaAuthenticatedMessage msg = new OKKoPaAuthenticatedMessage("okkopa.2013@gmail.com", "vaaralahettaja@gmail.com", props, "okkopa.2013@gmail.com", "ohtu2013okkopa");
        msg.setText("toimiikoä");
        msg.setText("yksi viesti");
        msg.setSubject("testi123");
        msg.addAttachment("liite.txt");
        msg.send();
    }
    
}
