package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.mail.Message;
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
    private Stage nextStage;

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
        nextStage = mock(Stage.class);
        getEmailStage.setNext(nextStage);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConnectAndClose() throws NoSuchProviderException, MessagingException, MessagingException, IOException {
        getEmailStage.process(null);
        verify(emailReadMock, times(1)).connect();
        verify(emailReadMock, times(1)).closeQuietly();
        verify(nextStage, never()).process(any());
    }

    @Test
    public void testList() throws NoSuchProviderException, MessagingException, MessagingException, IOException {
        InputStream is = mock(InputStream.class);
        ArrayList<InputStream> list = new ArrayList<>();
        list.add(is);
        when(emailReadMock.getNextMessage()).thenReturn(mock(Message.class), null);
        when(emailReadMock.getMessagesAttachments(any(Message.class))).thenReturn(list);
        getEmailStage.process(null);
        verify(nextStage, times(1)).process(any());
        verify(exceptionLoggerMock, never()).logException(any(Exception.class));
    }

    @Test
    public void testList2() throws NoSuchProviderException, MessagingException, MessagingException, IOException {
        InputStream is = mock(InputStream.class);
        ArrayList<InputStream> list = new ArrayList<>();
        list.add(is);
        list.add(is);
        list.add(is);
        list.add(is);
        list.add(is);
        when(emailReadMock.getNextMessage()).thenReturn(mock(Message.class), null);
        when(emailReadMock.getMessagesAttachments(any(Message.class))).thenReturn(list);
        getEmailStage.process(null);
        verify(nextStage, times(5)).process(any());
        verify(exceptionLoggerMock, never()).logException(any(Exception.class));
    }

    @Test
    public void testServerThrowsException() throws NoSuchProviderException, MessagingException, IOException {
        doThrow(new MessagingException()).when(emailReadMock).connect();
        getEmailStage.process(null);
        verify(emailReadMock, never()).getNextMessage();
        verify(emailReadMock, times(1)).closeQuietly();
        verify(exceptionLoggerMock, times(1)).logException(any(Exception.class));
    }
}