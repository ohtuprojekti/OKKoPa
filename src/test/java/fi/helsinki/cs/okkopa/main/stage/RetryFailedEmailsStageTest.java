package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.FailedEmailDAO;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.mail.send.EmailSender;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RetryFailedEmailsStageTest {

    private EmailSender senderMock;
    private ExceptionLogger loggerMock;
    private Settings settingsMock;
    private Saver saverMock;
    private FailedEmailDAO dbMock;
    private RetryFailedEmailsStage retryStage;
    private Stage nextStageMock;

    public RetryFailedEmailsStageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.senderMock = mock(EmailSender.class);
        this.loggerMock = mock(ExceptionLogger.class);
        this.settingsMock = mock(Settings.class);
        this.saverMock = mock(Saver.class);
        this.dbMock = mock(FailedEmailDAO.class);
        when(settingsMock.getProperty("mail.send.retryexpirationminutes")).thenReturn("5");
        this.retryStage = new RetryFailedEmailsStage(senderMock, loggerMock, settingsMock, saverMock, dbMock);
        this.nextStageMock = mock(Stage.class);
        this.retryStage.setNext(nextStageMock);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetsFileList() throws SQLException {
        when(saverMock.list(anyString())).thenReturn(null);
        this.retryStage.process(null);
        verify(saverMock, times(1)).list(anyString());
        // stops other actions
        verify(dbMock, never()).listAll();
        // continue to next stage
        verify(nextStageMock, times(1)).process(any());
    }
    
    @Test
    public void testGetsDBList() throws SQLException {
        when(saverMock.list(anyString())).thenReturn(new ArrayList());
        this.retryStage.process(null);
        verify(saverMock, times(1)).list(anyString());
        verify(dbMock, times(1)).listAll();
        verify(nextStageMock, times(1)).process(any());
    }
    
    @Test
    public void testDBListError() throws SQLException {
        when(saverMock.list(anyString())).thenReturn(new ArrayList());
        doThrow(new SQLException()).when(dbMock).listAll();
        this.retryStage.process(null);
        verify(saverMock, times(1)).list(anyString());
        verify(dbMock, times(1)).listAll();
        verify(loggerMock, times(1)).logException(any(SQLException.class));
        verify(nextStageMock, times(1)).process(any());
    }
    
    //TODO retryfailedemail part
}