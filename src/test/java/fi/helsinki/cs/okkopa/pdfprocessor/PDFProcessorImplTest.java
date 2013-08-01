/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author heha
 */
public class PDFProcessorImplTest {
    
    PDFProcessorImpl processor;
    
    @Before
    public void setUp() {
        processor = new PDFProcessorImpl(new PDFSplitter(), new QRCodeReader());
    }

    @Test
    public void testReadQRCode() throws DocumentException {
        List<ExamPaper> splitPDF = processor.splitPDF(getClass().getResourceAsStream("/pdf/all.pdf"));
        int errors = 0;
        for (ExamPaper examPaper : splitPDF) {
            try {
                processor.readQRCode(examPaper);
            } catch (NotFoundException ex) {
                errors++;
            }
        }
        assertEquals(1, errors);
    }
}