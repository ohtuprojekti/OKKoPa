/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.read;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserException;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import java.security.Security;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

public class IMAPmessageTest {
    private GreenMail greenMail;
    private GreenMailUser user;
    private IMAPserver server;
    private IMAPfolder IMAPfolder;
    private IMAPmessage IMAPmessage;
    
    public IMAPmessageTest() {
    }
    
    @Before
    public void setUp() throws MessagingException, UserException, InterruptedException {
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
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
        
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        
        server = new IMAPserver("localhost", "okkopa", "soooosecret", 4008);
        server.login();
        
        IMAPfolder = new IMAPfolder(server, "inbox");
    }
    
    @After
    public void tearDown() throws MessagingException {
        server.close();
        greenMail.stop();
    }
    
//    @Test
//    public void getSubject() throws MessagingException {
//        IMAPFolder inboxFolder = IMAPfolder.getIMAPFolder();
//        Message[] messages = inboxFolder.getMessages();
//        
////        inboxFolder.open(Folder.READ_WRITE);
//        
////        Message[] messages = greenMail.getReceivedMessages();
//        
//        System.out.println("viestien määrä " + messages.length);
//        
//        System.out.println("viestin numero " + messages[0].getMessageNumber());
//        
//        System.out.println("viestin otsikko " + messages[0].getSubject());
//        
//        //IMAPmessage = new IMAPmessage(messages[0]);
//        
////        assertEquals("subject2576Hf", messages[0].getSubject());
//    }
}