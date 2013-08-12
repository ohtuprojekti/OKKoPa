package fi.helsinki.cs.okkopa.pdfprocessor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;

/**
 *
 * @author hannahir
 */
public class PDFCreator {
    
    
    
    /**
     *
     * @param pageImages
     * @return
     * @throws IOException
     * @throws COSVisitorException
     */
    public InputStream createPDF(List<BufferedImage> pageImages) throws IOException, COSVisitorException {
        PDDocument document = new PDDocument();
        
        for (BufferedImage pageImage : pageImages) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            PDPixelMap ximage = new PDPixelMap(document, pageImage);
            content.drawImage(ximage, 0, 0);
            content.close();
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        document.save(os);
        document.close();
        return new ByteArrayInputStream(os.toByteArray());
    }
}
