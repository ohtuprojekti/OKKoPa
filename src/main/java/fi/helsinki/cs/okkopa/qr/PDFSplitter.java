package fi.helsinki.cs.okkopa.qr;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;
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
     * @return A List of document's pages converted to BufferedImages.
     * @throws IOException If InputStream can not be read or stream doesn't
     * contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     */
    public List<ExamPaper> splitPdf(InputStream pdfStream) throws IOException, DocumentException, RendererException {
        PDFDocument document = new PDFDocument();
        document.load(pdfStream);
        if (document.getPageCount() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(300);

        List<Image> pageImages = renderer.render(document);

        ArrayList<ExamPaper> examPapers = new ArrayList<>();
        for (int i = 0; i < pageImages.size(); i++) {
            if (i % 2 == 0) {
                ExamPaper examPaper = new ExamPaper();
                examPaper.setPageImages(new ArrayList<BufferedImage>());
            }
            examPapers.get(examPapers.size()-1).getPageImages().add((BufferedImage) pageImages.get(i));
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
//
//    private List<PDFDocument> splitExams(PDDocument document) throws IOException {
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
