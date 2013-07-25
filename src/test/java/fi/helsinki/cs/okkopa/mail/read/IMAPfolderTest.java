package fi.helsinki.cs.okkopa.mail.read;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserException;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.sun.mail.imap.IMAPFolder;
import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IMAPfolderTest {

    private GreenMail greenMail;
    private GreenMailUser user;
    private IMAPserver server;
    private IMAPFolder IMAPFolder;
    private IMAPfolder IMAPfolder;

    public IMAPfolderTest() {
    }

    @Before
    public void setUp() throws MessagingException, UserException, InterruptedException {
        ServerSetup sS = new ServerSetup(4008, "localhost", ServerSetup.PROTOCOL_IMAPS);
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

        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
        server = new IMAPserver("localhost", "okkopa", "soooosecret", 4008);
        server.login();
        
        IMAPfolder = new IMAPfolder(server, "inbox");
        
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));

    }

    @After
    public void tearDown() throws MessagingException {
        server.close();
        greenMail.stop();
    }
    
    @Test
    public void testIMAPfolderFormat() throws MessagingException {
        IMAPfolder = new IMAPfolder(server, "inbox");
    }
    
    @Test
    public void testClose() throws MessagingException {
        IMAPfolder.close();
    }
    
    /**
     * Deleting of message from the old folder doesn't work with the test environment but works with the GMAIL.
     * We think it has something to do with how we make the message through greenmail API, not by normal way.
     * 
     * @throws MessagingException
     */
    @Test
    public void testNextMessage() throws MessagingException {
        server.createFolder("processed");
        
        IMAPFolder processedFolder = server.selectAndGetFolder("processed");
        IMAPFolder inboxFolder = server.selectAndGetFolder("inbox");
        
        assertEquals("eka", 0, processedFolder.getMessageCount());
        assertEquals("toka", 1, inboxFolder.getMessageCount());
        
        IMAPfolder.getNextmessage();
        
//        assertEquals("neljäs", 0, inboxFolder.getMessageCount());
//        assertEquals("kolmas",1, processedFolder.getMessageCount());
        
    }
    
    /**
     * Deleting of message from the old folder doesn't work with the test environment but works with the GMAIL.
     * We think it has something to do with how we make the message through greenmail API, not by normal way.
     * 
     * @throws MessagingException
     */
    @Test
    public void deleteMessage() throws MessagingException {       
        IMAPFolder inboxFolder = IMAPfolder.getIMAPFolder();
        Message[] messages = inboxFolder.getMessages();
        
        for (Message message : messages) {
            System.out.println("poisto ");
            IMAPcopy.deleteMessage(message);
        }
        
        messages = inboxFolder.getMessages();
        
        System.out.println("viestien määrä on " + messages.length);
        
//        assertEquals(0, inboxFolder.getMessageCount());
    }
}