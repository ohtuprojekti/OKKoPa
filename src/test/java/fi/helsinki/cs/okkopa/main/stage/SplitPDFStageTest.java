package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.main.BatchDetails;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.InputStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SplitPDFStageTest {

    private ExceptionLogger exceptionLoggerMock;
    private PDFProcessor pdfProcessorMock;
    private SplitPDFStage splitPDFStage;
    private Stage nextStage;
    private BatchDetails batchMock;

    public SplitPDFStageTest() {
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
        pdfProcessorMock = mock(PDFProcessor.class);
        batchMock = mock(BatchDetails.class);
        splitPDFStage = new SplitPDFStage(pdfProcessorMock, exceptionLoggerMock, batchMock);
        nextStage = mock(Stage.class);
        splitPDFStage.setNext(nextStage);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSplit() throws DocumentException {
        InputStream isMock = mock(InputStream.class);
        splitPDFStage.process(isMock);
        verify(pdfProcessorMock, times(1)).splitPDF(isMock);
        verify(nextStage, times(1)).process(any());
    }

    @Test
    public void testSplitResult() throws DocumentException {
        InputStream isMock = mock(InputStream.class);
        ArrayList<ExamPaper> list = new ArrayList<>();
        when(pdfProcessorMock.splitPDF(isMock)).thenReturn(list);
        splitPDFStage.process(isMock);
        verify(pdfProcessorMock, times(1)).splitPDF(isMock);
        verify(nextStage, times(1)).process(list);
        verify(exceptionLoggerMock, never()).logException(any(DocumentException.class));
    }

    @Test
    public void testSplitError() throws DocumentException {
        InputStream isMock = mock(InputStream.class);
        doThrow(new DocumentException()).when(pdfProcessorMock).splitPDF(isMock);
        splitPDFStage.process(isMock);
        verify(nextStage, never()).process(any());
        verify(exceptionLoggerMock, times(1)).logException(any(DocumentException.class));
    }
}