package fi.helsinki.cs.okkopa.qr;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.ghost4j.document.DocumentException;
import org.ghost4j.renderer.RendererException;
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
    public void wrongFileType() throws IOException, DocumentException, RendererException {
        InputStream file = getClass().getResourceAsStream("/text/testEmpty");
        splitter.splitPdf(file);
    }

    /**
     * Test loading a PDF with odd number of pages.
     */
    @Test(expected = DocumentException.class)
    public void oddPages() throws IOException, DocumentException, RendererException {
        InputStream file = getClass().getResourceAsStream("/pdf/three_page.pdf");
        splitter.splitPdf(file);
    }

    /**
     * Test loading an eligible PDF with even number of pages.
     */
    @Test
    public void eligibleDocument() throws IOException, DocumentException, RendererException {
        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(file);
        assertEquals(8, examPapers.size());
    }
    
//    @Test
//    public void jbig2Document() throws IOException, org.ghost4j.document.DocumentException, RendererException {
//        InputStream file = getClass().getResourceAsStream("/pdf/jbig2.pdf");
//        List<ExamPaper> examPapers = splitter.splitPdf(file);
//        assertEquals(8, examPapers.size());
//    }
}
