package fi.helsinki.cs.okkopa.qr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

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
     *                      contain a PDF-format file.
     * @throws DocumentException If document contains odd number of pages.
     */
    public List<BufferedImage> splitPdf(InputStream pdfStream) throws IOException, DocumentException {
        PDDocument document = PDDocument.load(pdfStream);
        if (document.getNumberOfPages() % 2 != 0) {
            throw new DocumentException("Odd number of pages");
        }
        List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
        ArrayList<BufferedImage> pages = new ArrayList<>();
        for (PDPage pdPage : pdPages) {
            pages.add(pdPage.convertToImage());
        }        
        document.close();
        return pages;
    }
}
