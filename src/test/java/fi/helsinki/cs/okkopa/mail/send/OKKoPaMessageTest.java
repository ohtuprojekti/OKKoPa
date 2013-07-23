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
public class OKKoPaMessageTest {
    
    GreenMail greenMail;
    
    public OKKoPaMessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws MessagingException {
        greenMail = new GreenMail(ServerSetup.SMTP); //uses test ports by default
        greenMail.start();
    }
    
    @After
    public void tearDown() {
        if (null!=greenMail) {
            greenMail.stop();
        }
    }

    @Test
    public void testSetText() throws Exception {
        OKKoPaMessage msg = new OKKoPaMessage("a", "b");
        msg.setText("viestin sisalto");
        assertEquals(msg.body.getBodyPart(0).getContent().toString(), "viestin sisalto");
    }

    @Test
    public void testSetSubject() throws Exception {
        OKKoPaMessage msg = new OKKoPaMessage("a", "b");
        msg.setSubject("aihe");
        assertEquals(msg.subject, "aihe");
    }

    @Test
    public void testGenerateSession() {
        
    }

    @Test
    public void testGenerateMessage() throws Exception {
    }

    @Test
    public void testSend() throws Exception {
        Properties props = Settings.SMTPPROPS;
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.port", ""+greenMail.getSmtp().getPort());
        OKKoPaMessage msg = new OKKoPaMessage("a", "b", null);
        msg.setSubject("greenmail testi");
        msg.setText("sisältö");
        msg.send();
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        System.out.println(messages.length + " messages");
    }

    @Test
    public void testAddAttachment() throws Exception {
    }

    @Test
    public void testMain() throws Exception {
    }
}