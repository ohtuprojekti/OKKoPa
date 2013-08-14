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
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
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
}
