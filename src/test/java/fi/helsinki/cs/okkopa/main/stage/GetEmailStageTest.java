package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GetEmailStageTest {
    
    private GetEmailStage getEmailStage;
    private EmailRead emailReadMock;
    private ExceptionLogger exceptionLoggerMock;
    
    public GetEmailStageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        emailReadMock = mock(EmailRead.class);
        exceptionLoggerMock = mock(ExceptionLogger.class);
        getEmailStage = new GetEmailStage(emailReadMock, exceptionLoggerMock);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConnectAndClose() throws NoSuchProviderException, MessagingException, MessagingException, IOException {
        when(emailReadMock.getNextMessagesAttachments()).thenReturn(null);
        getEmailStage.process(null);
        verify(emailReadMock, times(1)).connect();
        verify(emailReadMock, times(1)).close();
    }
}