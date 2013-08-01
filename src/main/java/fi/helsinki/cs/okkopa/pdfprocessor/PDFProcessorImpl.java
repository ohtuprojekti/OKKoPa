package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PDFProcessorImpl implements PDFProcessor {

    private PDFSplitter splitter;
    private QRCodeReader reader;
    private final double[] SCALERS = {1.0 / 3.0, 1.0, 2.5 / 3.0, 2.0 / 3.0, 1.5 / 3.0};

    @Autowired
    public PDFProcessorImpl(PDFSplitter splitter, QRCodeReader reader) {
        this.splitter = splitter;
        this.reader = reader;
    }

    /**
     *
     * @param pdfStream
     * @return
     * @throws DocumentException If there were problems reading or splitting the
     * document.
     */
    @Override
    public List<ExamPaper> splitPDF(InputStream pdfStream) throws DocumentException {
        try {
            return splitter.splitPdfToExamPapersWithImages(pdfStream);
        } catch (IOException | PdfException | DocumentException ex) {
            throw new DocumentException(ex.getMessage());
        }
    }

    @Override
    public String readQRCode(ExamPaper examPaper) throws NotFoundException {
        Exception e = new Exception();
        List<BufferedImage> pageImages = examPaper.getPageImages();
        for (double scaler : SCALERS) {
            for (BufferedImage pageImage : pageImages) {
                int newWidth = (int) (pageImage.getWidth() * scaler);
                int newHeight = (int) (pageImage.getHeight() * scaler);
                BufferedImage resized = new BufferedImage(newWidth, newHeight, pageImage.getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(pageImage, 0, 0, newWidth, newHeight, 0, 0, pageImage.getWidth(), pageImage.getHeight(), null);
                g.dispose();
                try {
                    return reader.readQRCode(resized).getText();
                } catch (com.google.zxing.NotFoundException | ChecksumException | FormatException ex) {
                    e = ex;
                }
            }
        }
        throw new NotFoundException(e.getMessage());
    }
}
