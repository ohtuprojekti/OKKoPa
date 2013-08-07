package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;
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
        pdf.setExtractionMode(PdfDecoder.FINALIMAGES);

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

    public List<ExamPaper> splitToExamPapersWithPDFStreams(InputStream pdfStream) throws IOException, DocumentException, PdfException, COSVisitorException {
        PDDocument allPdfDocument = PDDocument.load(pdfStream);
        if (allPdfDocument.getNumberOfPages() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }
        Splitter splitter = new Splitter();
        splitter.setSplitAtPage(2);
        List<PDDocument> pdfDocuments = splitter.split(allPdfDocument);
        ArrayList<ExamPaper> examPapers = new ArrayList<>();
        for (PDDocument pdfDocument : pdfDocuments) {
            ExamPaper paper = new ExamPaper();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfDocument.save(out);
            byte[] data = out.toByteArray();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ByteArrayInputStream in2 = new ByteArrayInputStream(data);
            paper.setSplitPdfStream(in);
            paper.setPdfStream(in2);
            examPapers.add(paper);
            pdfDocument.close();
        }
        allPdfDocument.close();
        return examPapers;
    }
}
