package fi.helsinki.cs.okkopa.mail.read;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import fi.helsinki.cs.okkopa.main.Settings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmailReadImplTest {

    private EmailReadImpl emailReader;
    private GreenMail greenMail;
    private GreenMailUser user;
    private byte[] pdf;

    public EmailReadImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException, MessagingException {
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        ServerSetup imap = new ServerSetup(4008, "localhost", ServerSetup.PROTOCOL_IMAPS);
        ServerSetup smtp = new ServerSetup(4009, "localhost", ServerSetup.PROTOCOL_SMTPS);
        ServerSetup[] setups = {imap, smtp};
        greenMail = new GreenMail(setups);
        greenMail.start();
        user = greenMail.setUser("okkopa@okkopa.ok", "testi");
        Settings settings = new Settings("imaptestsettings.xml");
        settings.setProperty("mail.imap.password", "testi");
        emailReader = new EmailReadImpl(settings);
        InputStream is = getClass().getResourceAsStream("/pdf/all.pdf");
        pdf = IOUtils.toByteArray(is);
        is.close();
        GreenMailUtil.sendAttachmentEmail("okkopa@okkopa.ok", "from@localhost.com", "subject", "message",
                pdf, "application/pdf", "testi.pdf", "pdf-file", smtp);
    }

    @After
    public void tearDown() {
        try {
            emailReader.close();
        } catch (Exception ex) {
            // ignore
        }
        greenMail.stop();
    }

    /**
     * Test of close method, of class EmailReadImpl.
     */
    @Test(expected = NullPointerException.class)
    public void testClose() throws MessagingException {
        // should throw null pointer because connect was not called before this
        emailReader.close();
    }

    /**
     * Test of connect method, of class EmailReadImpl.
     */
    @Test
    public void testConnect() throws NoSuchProviderException, MessagingException {
        // should just connect without exceptions
        emailReader.connect();
    }

    /**
     * Test of getNextMessage method, of class EmailReadImpl.
     */
    @Test
    public void testGetNextMessage() throws MessagingException {
        emailReader.connect();
        Message nextMessage = emailReader.getNextMessage();
        assertEquals("subject", nextMessage.getSubject());
        assertEquals("from@localhost.com", nextMessage.getFrom()[0].toString());
    }

    /**
     * Test of getMessagesAttachments method, of class EmailReadImpl.
     */
    @Test
    public void testGetMessagesAttachments() throws NoSuchProviderException, MessagingException, IOException {
        emailReader.connect();
        Message nextMessage = emailReader.getNextMessage();
        List<InputStream> messagesAttachments = emailReader.getMessagesAttachments(nextMessage);
        byte[] toByteArray = IOUtils.toByteArray(messagesAttachments.get(0));
        assertArrayEquals(pdf, toByteArray);
    }

    /**
     * Test of cleanUpMessage method, of class EmailReadImpl.
     */
    @Test
    public void testCleanUpMessage() throws MessagingException {
        emailReader.connect();
        Message nextMessage = emailReader.getNextMessage();
        assertEquals(1, greenMail.getReceivedMessages().length);
        emailReader.cleanUpMessage(nextMessage);
        assertEquals(0, greenMail.getReceivedMessages().length);
    }

    /**
     * Test of closeQuietly method, of class EmailReadImpl.
     */
    @Test
    public void testCloseQuietly() {
        // shouldn't throw anything (not even null pointer)
        emailReader.closeQuietly();
    }
}