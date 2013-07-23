package fi.helsinki.cs.okkopa.qr;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hannahir
 */
public class PDFSplitterTest {
    PDFSplitter splitter;
    
    @Before
    public void setUp() {
        splitter = new PDFSplitter();
    }
    
    @Test (expected = IOException.class)
    public void wrongFileType() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/text/testEmpty");
        splitter.splitPdf(file);     
    }
    
    @Test (expected = Exception.class)
    public void unevenPages() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/three_page.pdf");
        splitter.splitPdf(file);     
    }
    @Test
    public void eligibleDocument() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/all.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(file); 
        assertEquals(8, examPapers.size());
    }
   
}
