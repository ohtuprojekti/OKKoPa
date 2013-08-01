package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.springframework.stereotype.Component;

/**
 * Converts a PDF-document to a list of images, one per page.
 */
@Component
public class PDFSplitter {

    /**
     * Converts a PDF-document to a list of images, one per page.
     *
     * @param pdfStream PDF-file as InputStream
     * @return A List of ExamPapers with two BufferedImages in a list.
     * @throws IOException If InputStream can not be read or stream doesn't
     * contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     */
    public List<ExamPaper> splitPdfToExamPapersWithImages(InputStream pdfStream) throws IOException, DocumentException, PdfException {

        PdfDecoder pdf = new PdfDecoder(true);
        pdf.openPdfFileFromInputStream(pdfStream, true);

        if (pdf.getPageCount() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }

        ArrayList<ExamPaper> examPapers = new ArrayList<>();
        ExamPaper examPaper = null;
        for (int i = 1; i <= pdf.getPageCount(); i++) {
            if (i % 2 != 0) {
                examPaper = new ExamPaper();
                examPapers.add(examPaper);
            }
            examPaper.addPageImage(pdf.getPageAsImage(i));
        }
        return examPapers;
    }
}
