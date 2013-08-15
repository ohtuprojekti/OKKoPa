/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FailedEmail;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import javax.mail.MessagingException;
import org.jpedal.exception.PdfException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ReadQRCodeStageTest {
   private ReadQRCodeStage readqrCodeStage; 
    private Settings settingsMock;
   
    private ExceptionLogger exceptionLoggerMock;
   private PDFProcessor pdfProcessorMock;
    private Saver saverMock;
    private ExamPaper examPaperMock;
    private Stage nextSatageMock;
    private String saveRetryFolder;
    
    public ReadQRCodeStageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        saverMock = mock(Saver.class);
        settingsMock = mock(Settings.class);
        pdfProcessorMock = mock(PDFProcessor.class);
        exceptionLoggerMock = mock(ExceptionLogger.class);
        examPaperMock = mock(ExamPaper.class);
        nextSatageMock = mock(Stage.class);
        saveRetryFolder = "testi";
        when(settingsMock.getProperty("mail.send.retrysavefolder")).thenReturn(saveRetryFolder);
        readqrCodeStage = new ReadQRCodeStage(saverMock, pdfProcessorMock, exceptionLoggerMock, settingsMock);
        readqrCodeStage.setNext(nextSatageMock);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class ReadQRCodeStage.
     */
    @Test
    public void testProcess() throws PdfException, NotFoundException {
        readqrCodeStage.process(examPaperMock);
        verify(examPaperMock, times(1)).setPageImages(pdfProcessorMock.getPageImages(examPaperMock));
        verify(examPaperMock, times(1)).setQRCodeString(pdfProcessorMock.readQRCode(examPaperMock));
        verify(nextSatageMock, times(1)).process(examPaperMock);
        
    }
    
    public void testProcessExecption() throws PdfException, NotFoundException, FileAlreadyExistsException{
        doThrow(new MessagingException()).when(examPaperMock).setPageImages(pdfProcessorMock.getPageImages(examPaperMock));
        doThrow(new MessagingException()).when(examPaperMock).setQRCodeString(pdfProcessorMock.readQRCode(examPaperMock));
        readqrCodeStage.process(examPaperMock);
         verify(examPaperMock, times(1)).setPageImages(pdfProcessorMock.getPageImages(examPaperMock));
        verify(examPaperMock, times(1)).setQRCodeString(pdfProcessorMock.readQRCode(examPaperMock));
        verify(exceptionLoggerMock, times(1)).logException(any(Exception.class));
        verify(saverMock, times(1)).saveInputStream(any(InputStream.class), eq(saveRetryFolder), anyString());
        verify(nextSatageMock, times(1)).process(examPaperMock);
    }
            
}
