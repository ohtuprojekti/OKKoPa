package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jpedal.exception.PdfException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PDFSplitterTest {

    PDFSplitter splitter;

    @Before
    public void setUp() {
        splitter = new PDFSplitter();
    }

    /**
     * Test loading a wrong type of file.
     */
    @Test(expected = IOException.class)
    public void wrongFileType() throws IOException, DocumentException, PdfException, COSVisitorException {
        InputStream file = getClass().getResourceAsStream("/text/testEmpty");
        splitter.splitToExamPapersWithPDFStreams(file);
    }

    /**
     * Test loading a PDF with odd number of pages.
     */
    @Test(expected = DocumentException.class)
    public void oddPages() throws IOException, DocumentException, PdfException, COSVisitorException {
        InputStream file = getClass().getResourceAsStream("/pdf/three_page.pdf");
        splitter.splitToExamPapersWithPDFStreams(file);
    }

    /**
     * Test loading an eligible PDF with even number of pages.
     */
    @Test
    public void eligibleDocument() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/packed4.pdf");
        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
        assertEquals(20, examPapers.size());
    }
    
    /**
     * Test checking input stream in each exam paper not null.
     */
    @Test
    public void imagesNotNull() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
        for (ExamPaper examPaper : examPapers) {
            assertTrue(examPaper.getSplitPdfStream().available() > 0);
        }
    }
         
    /**
     * Test checking single paper containing two pages per exam paper.
     */
//    @Test
//    public void twoPapersPerPDF() throws IOException, Exception {
//        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
//        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
//        for (ExamPaper examPaper : examPapers) {
//            assertEquals(2, examPaper.getPageImages().size());
//        }
//    }
    
    /**
     * Test images having size and height.
     */
//    @Test
//    public void imagesHaveSize() throws IOException, Exception {
//        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
//        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
//        for (ExamPaper examPaper : examPapers) {
//            for (BufferedImage image : examPaper.getPageImages()) {
//                assertFalse(image.getHeight() == 0);
//                assertFalse(image.getWidth() == 0);
//            }
//        }
//    } 
}
