package fi.helsinki.cs.okkopa.qr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.Splitter;

/**
 * Converts a PDF-document to a list of images, one per page.
 */
public class PDFSplitter {

    /**
     * Converts a PDF-document to a list of images, one per page.
     *
     * @param pdfStream PDF-file as InputStream
     * @return A List of document's pages converted to BufferedImages.
     * @throws IOException If InputStream can not be read or stream doesn't
     * contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     */
    public List<ExamPaper> splitPdf(InputStream pdfStream) throws IOException, DocumentException, COSVisitorException {
        PDDocument document = PDDocument.load(pdfStream);
        if (document.getNumberOfPages() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }
        List<PDDocument> examPdfs = splitExams(document);

        ArrayList<ExamPaper> examPapers = new ArrayList<>();
        for (PDDocument examPdf : examPdfs) {
            ExamPaper examPaper = new ExamPaper();
            examPaper.setPageImages(extractImages(examPdf));
            examPaper.setPdfStream(getPdfStream(examPdf));
            examPapers.add(examPaper);
            examPdf.close();
        }
        document.close();
        return examPapers;
    }

    private ArrayList<BufferedImage> extractImages(PDDocument document) throws IOException {
        List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
        ArrayList<BufferedImage> pages = new ArrayList<>();
        for (PDPage pdPage : pdPages) {
            pages.add(pdPage.convertToImage());
        }
        return pages;
    }

    private List<PDDocument> splitExams(PDDocument document) throws IOException {
        Splitter splitter = new Splitter();
        splitter.setSplitAtPage(2);
        List<PDDocument> exams = splitter.split(document);
        return exams;
    }

    private InputStream getPdfStream(PDDocument document) throws COSVisitorException, IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        document.save(os);
        return new ByteArrayInputStream(os.toByteArray());
    }
}
