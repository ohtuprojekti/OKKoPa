package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            paper.setPdf(data);
            examPapers.add(paper);
            pdfDocument.close();
        }
        allPdfDocument.close();
        return examPapers;
    }
}
