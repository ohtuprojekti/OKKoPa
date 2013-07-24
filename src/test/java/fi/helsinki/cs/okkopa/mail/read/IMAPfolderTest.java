package fi.helsinki.cs.okkopa.mail.read;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.sun.mail.imap.IMAPFolder;
import java.util.Properties;
import javax.mail.Folder;
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
    public void setUp() throws MessagingException, UserException {
        ServerSetup sS = new ServerSetup(4005, "localhost", ServerSetup.PROTOCOL_IMAPS);
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
        
        IMAPfolder = new IMAPfolder(server, "inbox");
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
    
    @Test
    public void testNextMessage() throws MessagingException {
        server.createFolder("processed");
        
        IMAPFolder processedFolder = server.selectAndGetFolder("processed");
        IMAPFolder inboxFolder = server.selectAndGetFolder("inbox");
        
        System.out.println("eka");
        assertEquals(processedFolder.getMessageCount(), 0);
        System.out.println("toka");
        assertEquals(inboxFolder.getMessageCount(), 1);
        
        IMAPfolder.getNextmessage();
        IMAPfolder.getNextmessage();
        
        System.out.println("kolme");
        assertEquals(processedFolder.getMessageCount(), 1);
        System.out.println("neljä");
        assertEquals(inboxFolder.getMessageCount(), 0);
    }
}