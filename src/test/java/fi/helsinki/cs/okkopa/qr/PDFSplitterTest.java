package fi.helsinki.cs.okkopa.qr;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
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
    public void wrongFileType() throws IOException, DocumentException, COSVisitorException {
        InputStream file = getClass().getResourceAsStream("/text/testEmpty");
        splitter.splitPdf(file);
    }

    /**
     * Test loading a PDF with odd number of pages.
     */
    @Test(expected = DocumentException.class)
    public void oddPages() throws IOException, DocumentException, COSVisitorException {
        InputStream file = getClass().getResourceAsStream("/pdf/three_page.pdf");
        splitter.splitPdf(file);
    }

    /**
     * Test loading an eligible PDF with even number of pages.
     */
    @Test
    public void eligibleDocument() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(file);
        assertEquals(8, examPapers.size());
    }
    
    /**
     * Test checking single paper containing two pages per exam paper.
     */
    @Test
    public void twoPapersPerPDF() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(file);
        for (ExamPaper examPaper : examPapers) {
            assertEquals(2, examPaper.getPageImages().size());
        }
    }
    
}
