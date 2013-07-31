package fi.helsinki.cs.okkopa.qr;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jpedal.PdfDecoder;
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
    @Test(expected = PdfException.class)
    public void wrongFileType() throws IOException, DocumentException, COSVisitorException, PdfException {
        InputStream file = getClass().getResourceAsStream("/text/testEmpty");
        splitter.splitPdf(file);
    }

    /**
     * Test loading a PDF with odd number of pages.
     */
    @Test(expected = DocumentException.class)
    public void oddPages() throws IOException, DocumentException, COSVisitorException, PdfException {
        InputStream file = getClass().getResourceAsStream("/pdf/three_page.pdf");
        splitter.splitPdf(file);
    }

    /**
     * Test loading an eligible PDF with even number of pages.
     */
    @Test
    public void eligibleDocument() throws IOException, Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/packed4.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(file);
        assertEquals(20, examPapers.size());
    }
}
