/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.send;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import fi.helsinki.cs.okkopa.Settings;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkaik
 */
public class OKKoPaAuthenticatedMessageTest {
    
    GreenMail greenMail;
    Properties props;
    private GreenMailUser user;
    
    public OKKoPaAuthenticatedMessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTP);
        greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
        user = greenMail.setUser("a@localhost.com", "testiuser", "testipassword");
        props = Settings.SMTPPROPS;
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "4012");
        props.put("mail.transport.protocol", "smtp");
    }
    

    @After
    public void tearDown() {
        if (null!=greenMail) {
            greenMail.stop();
        }
    }
    
    
    @Test
    public void testReceiveAnything() throws MessagingException, InterruptedException {
        OKKoPaAuthenticatedMessage msg = new OKKoPaAuthenticatedMessage("a@localhost.com", "a@localhost.com", props, "testiuser", "testipassword");
        msg.setSubject("greenmail testi");
        msg.setText("sisältö");
        msg.send();
        greenMail.waitForIncomingEmail(5000, 1);
        MimeMessage msg2 = greenMail.getReceivedMessages()[0];
        System.out.println("Senderrrrrrr: " + msg2.getSender());
        assertEquals(1, greenMail.getReceivedMessages().length);
    }
    
    
    @Test
    public void testWrongPassword() throws MessagingException, InterruptedException {
        OKKoPaAuthenticatedMessage msg = new OKKoPaAuthenticatedMessage("b@localhost.com", "asda@localhost.com", props, "testiuserrr", "wrongpassword");
        msg.setSubject("greenmail testi");
        msg.setText("sisältö");
        msg.send();
        greenMail.waitForIncomingEmail(5000, 1);
        MimeMessage msg2 = greenMail.getReceivedMessages()[0];
        System.out.println("Sender: " + msg2.getSender());
        //assertEquals(0, greenMail.getReceivedMessages().length);
    }

    
}