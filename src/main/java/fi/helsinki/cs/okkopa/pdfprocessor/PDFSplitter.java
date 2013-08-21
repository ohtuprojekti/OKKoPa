package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;
import org.jpedal.exception.PdfException;
import org.springframework.stereotype.Component;

@Component
public class PDFSplitter {

    /**
     * Splits a PDF-document into exam papers.
     *
     * @param pdfStream PDF-file as InputStream
     * @return A List of ExamPapers.
     * @throws IOException If InputStream can not be read or stream doesn't
     * contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     * @throws PdfException If a document is not in the right format or error
     * occurs while loading or splitting or document has an odd number of pages.
     * @throws COSVisitorException if something goes wrong when visiting a PDF
     * object.
     */
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
