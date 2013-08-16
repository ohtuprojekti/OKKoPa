package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.IOUtils;
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

    @Test
    public void pdfStreamWorks() throws Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/packed4.pdf");
        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
        InputStream pdf = new ByteArrayInputStream(examPapers.get(0).getPdf());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IOUtils.copy(pdf, os);
        assertEquals(75077, os.toByteArray().length);
    }

    @Test
    public void pdfStreamWorksMultipleTimes() throws Exception {
        InputStream file = getClass().getResourceAsStream("/pdf/packed4.pdf");
        List<ExamPaper> examPapers = splitter.splitToExamPapersWithPDFStreams(file);
        InputStream pdf = new ByteArrayInputStream(examPapers.get(0).getPdf());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        IOUtils.copy(pdf, os);
        assertEquals(75077, os.toByteArray().length);

        InputStream pdf2 = new ByteArrayInputStream(examPapers.get(0).getPdf());
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        IOUtils.copy(pdf2, os2);
        assertEquals(75077, os.toByteArray().length);
    }
}
