package fi.helsinki.cs.okkopa.qr;

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
 * Converts a PDF-document to a list of images.
 */
@Component
public class PDFSplitter {

    /**
     * Converts a PDF-document to a list of images, one per page.
     *
     * @param pdfStream PDF-file as InputStream
     * @return A List of document's pages converted to BufferedImages.
     * @throws IOException If InputStream can not be read or stream doesn't
     * contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     * @throws COSVisitorException If something goes wrong with the InputStream 
     * of PDF-file.
     */
    public List<ExamPaper> splitPdf(InputStream pdfStream) throws IOException, DocumentException, PdfException {
        
        PdfDecoder pdf = new PdfDecoder(true);
        pdf.openPdfFileFromInputStream(pdfStream, true);
        
        if (pdf.getPageCount() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }

        ArrayList<ExamPaper> examPapers = new ArrayList<>();
        
        for (int i = 0; i < pdf.getPageCount() / 2; i++) {
            ExamPaper examPaper = new ExamPaper();
            
            examPaper.addPageImage(pdf.getPageAsImage(2 * i));
            examPaper.addPageImage(pdf.getPageAsImage(2 * i + 1));
            
            //examPaper.setPdfStream(pdfStream);
            examPapers.add(examPaper);
        }
        return examPapers;
    }

//    private ArrayList<BufferedImage> extractImages(PDDocument document) throws IOException {
//        List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
//        ArrayList<BufferedImage> pages = new ArrayList<>();
//        for (PDPage pdPage : pdPages) {
//            pages.add(pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 100));
//            pages.add(pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 50));
//        }
//        return pages;
//    }

//    private List<PDDocument> splitExams(PDDocument document) throws IOException {
//        Splitter splitter = new Splitter();
//        splitter.setSplitAtPage(2);
//        List<PDDocument> exams = splitter.split(document);
//        return exams;
//    }
//
//    private InputStream getPdfStream(PDDocument document) throws COSVisitorException, IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        document.save(os);
//        return new ByteArrayInputStream(os.toByteArray());
//    }
}
