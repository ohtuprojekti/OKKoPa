/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.FailedEmailDAO;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.mail.send.EmailSender;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FailedEmailDbModel;
import fi.helsinki.cs.okkopa.model.Student;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SendEmailStageTest {

    private SendEmailStage sendEmailStage;
    private ExceptionLogger exceptionLoggerMock;
    private ExamPaper examPaperMock;
    private EmailSender emailSenderMock;
    private Settings settingsMock;
    private Saver saverMock;
    private FailedEmailDAO failedEmailDatabaseMock;
    private Stage nextSatageMock;
    private Student studentMock;
    private String saveRetryFolder;

    public SendEmailStageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        exceptionLoggerMock = mock(ExceptionLogger.class);
        examPaperMock = mock(ExamPaper.class);
        emailSenderMock = mock(EmailSender.class);
        settingsMock = mock(Settings.class);
        saverMock = mock(Saver.class);
        nextSatageMock = mock(Stage.class);
        studentMock = mock(Student.class);
        failedEmailDatabaseMock = mock(FailedEmailDAO.class);
        saveRetryFolder = "testi";
        when(settingsMock.getProperty("mail.send.retrysavefolder")).thenReturn(saveRetryFolder);
        when(examPaperMock.getPdf()).thenReturn(new byte[1]);
        when(examPaperMock.getStudent()).thenReturn(studentMock);
        sendEmailStage = new SendEmailStage(emailSenderMock, exceptionLoggerMock, settingsMock, saverMock, failedEmailDatabaseMock);
        sendEmailStage.setNext(nextSatageMock);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class SendEmailStage.
     */
    @Test
    public void testProcess() throws MessagingException {
        when(studentMock.getEmail()).thenReturn("testi");
        sendEmailStage.process(examPaperMock);
        verify(emailSenderMock, times(1)).send(eq("testi"), any(InputStream.class));
        verify(nextSatageMock, times(1)).process(examPaperMock);
    }

    @Test
    public void testProcessSaveFailedEmail() throws MessagingException, FileAlreadyExistsException, SQLException {
        doThrow(new MessagingException()).when(emailSenderMock).send(anyString(), any(InputStream.class));
        sendEmailStage.process(examPaperMock);
        verify(emailSenderMock, times(1)).send(anyString(), any(InputStream.class));
        verify(exceptionLoggerMock, times(1)).logException(any(Exception.class));
        verify(saverMock, times(1)).saveInputStream(any(InputStream.class), eq(saveRetryFolder), anyString());
        verify(failedEmailDatabaseMock, times(1)).addFailedEmail(any(FailedEmailDbModel.class));
        verify(nextSatageMock, times(1)).process(examPaperMock);

    }
}