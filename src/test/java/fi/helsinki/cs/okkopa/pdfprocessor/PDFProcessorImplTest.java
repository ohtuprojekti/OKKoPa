package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.util.List;
import org.jpedal.exception.PdfException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PDFProcessorImplTest {
    
    PDFProcessorImpl processor;
    
    @Before
    public void setUp() {
        processor = new PDFProcessorImpl(new PDFSplitter(), new QRCodeReader());
    }

    @Test
    public void testReadQRCode() throws DocumentException, PdfException {
        List<ExamPaper> splitPDF = processor.splitPDF(getClass().getResourceAsStream("/pdf/all.pdf"));
        int errors = 0;
        for (ExamPaper examPaper : splitPDF) {
            try {
                examPaper.setPageImages(processor.getPageImages(examPaper));
                processor.readQRCode(examPaper);
            } catch (NotFoundException ex) {
                errors++;
            }
        }
        assertEquals(1, errors);
    }
}