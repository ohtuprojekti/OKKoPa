package fi.helsinki.cs.okkopa.main;

import fi.helsinki.cs.okkopa.main.stage.DeleteOldErrorPDFsStage;
import fi.helsinki.cs.okkopa.main.stage.GetEmailStage;
import fi.helsinki.cs.okkopa.main.stage.ReadCourseInfoStage;
import fi.helsinki.cs.okkopa.main.stage.ReadQRCodeStage;
import fi.helsinki.cs.okkopa.main.stage.RetryFailedEmailsStage;
import fi.helsinki.cs.okkopa.main.stage.SaveToTikliStage;
import fi.helsinki.cs.okkopa.main.stage.SendEmailStage;
import fi.helsinki.cs.okkopa.main.stage.SetStudentInfoStage;
import fi.helsinki.cs.okkopa.main.stage.SplitPDFStage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OkkopaRunnerTest {
    private OkkopaRunner runner;
    private DeleteOldErrorPDFsStage deleteOldErrorPdfsMock;
    private RetryFailedEmailsStage retryFailedEmailMock;
    private GetEmailStage getEmailMock;
    private SplitPDFStage splitPdfMock;
    private ReadCourseInfoStage readCourseInfoMock;
    private ReadQRCodeStage readQRCodeMock;
    private SetStudentInfoStage setStudentInfoMock;
    private SendEmailStage sendEmailMock;
    private SaveToTikliStage saveToTikliMock;
    
    public OkkopaRunnerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        deleteOldErrorPdfsMock = mock(DeleteOldErrorPDFsStage.class);
        retryFailedEmailMock = mock(RetryFailedEmailsStage.class);
        getEmailMock = mock(GetEmailStage.class);
        splitPdfMock = mock(SplitPDFStage.class);
        readCourseInfoMock = mock(ReadCourseInfoStage.class);
        readQRCodeMock = mock(ReadQRCodeStage.class);
        setStudentInfoMock = mock(SetStudentInfoStage.class);
        sendEmailMock = mock(SendEmailStage.class);
        saveToTikliMock = mock(SaveToTikliStage.class);
        runner = new OkkopaRunner(deleteOldErrorPdfsMock, retryFailedEmailMock,
                getEmailMock, splitPdfMock, readCourseInfoMock, readQRCodeMock,
                setStudentInfoMock, sendEmailMock, saveToTikliMock);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class OkkopaRunner.
     */
    @Test
    public void testRun() {
        runner.run();
        verify(deleteOldErrorPdfsMock, times(1)).process(any());
    }
    
    @Test
    public void constructorChainsStages() {
        verify(deleteOldErrorPdfsMock, times(1)).setNext(retryFailedEmailMock);
        verify(retryFailedEmailMock, times(1)).setNext(getEmailMock);
        verify(getEmailMock, times(1)).setNext(splitPdfMock);
        verify(splitPdfMock, times(1)).setNext(readCourseInfoMock);
        verify(readCourseInfoMock, times(1)).setNext(readQRCodeMock);
        verify(readQRCodeMock, times(1)).setNext(setStudentInfoMock);
        verify(setStudentInfoMock, times(1)).setNext(sendEmailMock);
        verify(sendEmailMock, times(1)).setNext(saveToTikliMock);
    }
}