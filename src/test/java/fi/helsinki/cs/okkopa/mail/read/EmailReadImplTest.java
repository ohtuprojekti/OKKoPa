package fi.helsinki.cs.okkopa.mail.read;

import fi.helsinki.cs.okkopa.main.Settings;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmailReadImplTest {
    private EmailReadImpl emailReader;
    
    public EmailReadImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        emailReader = new EmailReadImpl(new Settings("imaptestsettings.xml"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of close method, of class EmailReadImpl.
     */
    @Test
    public void testClose() {

    }

    /**
     * Test of connect method, of class EmailReadImpl.
     */
    @Test
    public void testConnect() {

    }

    /**
     * Test of getMessagesAttachments method, of class EmailReadImpl.
     */
    @Test
    public void testGetMessagesAttachments() {

    }

    /**
     * Test of getNextMessage method, of class EmailReadImpl.
     */
    @Test
    public void testGetNextMessage() {

    }

    /**
     * Test of cleanUpMessage method, of class EmailReadImpl.
     */
    @Test
    public void testCleanUpMessage() {

    }

    /**
     * Test of closeQuietly method, of class EmailReadImpl.
     */
    @Test
    public void testCloseQuietly() {

    }
}