package fi.helsinki.cs.okkopa.mail.read;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.sun.mail.imap.IMAPFolder;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IMAPserverTest {

    private GreenMail greenMail;
    private GreenMailUser user;
    private IMAPserver server;
    private IMAPFolder IMAPFolder;

    @Before
    public void setUp() throws Exception {
        ServerSetup sS = new ServerSetup(4006, "localhost", ServerSetup.PROTOCOL_IMAPS);
        greenMail = new GreenMail(sS);
        greenMail.start();
        user = greenMail.setUser("okkopa@localhost.com", "okkopa", "soooosecret");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setSubject("subject2576Hf");
        message.setText("viesti");
        user.deliver(message);

        XTrustProvider.install();
    }

    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void testGreenMailWorksSomehow() throws InterruptedException, MessagingException, UserException {
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        System.out.println("subject of first email " + messages[0].getSubject());
    }

    @Test
    public void testServerLogin() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "soooosecret");
        server.login();
        server.close();
    }

    @Test(expected = MessagingException.class)
    public void testServerLoginWithWrongUsername() throws MessagingException {
        server = new IMAPserver("localhost", "okkoppa", "soooosecret");
        server.login();
        server.close();
    }

    @Test(expected = MessagingException.class)
    public void testServerLoginWithWrongPassword() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "sooooosecret");
        server.login();
        server.close();
    }

    @Test
    public void testReturnsIMAPfolder() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "soooosecret");
        server.login();

        IMAPFolder = server.selectAndGetFolder("inbox");

        assertTrue(IMAPFolder.exists());

        server.close();
    }

    @Test(expected = MessagingException.class)
    public void testReturnsIMAPfolderThatDoesntExist() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "soooosecret");
        server.login();

        server.selectAndGetFolder("dfrhtsytr");
        
        assertEquals(IMAPFolder.exists(), false);

        server.close();
    }
    
    @Test
    public void testCreateNewFolder() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "soooosecret");
        server.login();
        
        server.createFolder("processed");
        server.selectAndGetFolder("processed");
        
        server.close();
    }
    
    @Test(expected = MessagingException.class)
    public void testCreateNewFolder2() throws MessagingException {
        server = new IMAPserver("localhost", "okkopa", "soooosecret");
        server.login();
        
        server.createFolder("processed");
        server.selectAndGetFolder("procesased");
        
        server.close();
    }
}
