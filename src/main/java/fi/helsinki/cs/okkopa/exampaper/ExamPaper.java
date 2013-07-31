package fi.helsinki.cs.okkopa.exampaper;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Single exam paper containing two-page PDDocument, QR code information
 * as a String and a list containing PDDocument extracted to two images.
 */
public class ExamPaper {

    private InputStream pdfStream;
    private String QRCodeString;
    private List<BufferedImage> pageImages;
    private boolean pageImagesIsSet;

    public String getQRCodeString() {
        return QRCodeString;
    }
    
    public void setQRCodeString(String QRCodeString) {
        this.QRCodeString = QRCodeString;
    }

    public InputStream getPdfStream() {
        return pdfStream;
    }

    public void setPdfStream(InputStream pdfStream) {
        this.pdfStream = pdfStream;
    }

    public List<BufferedImage> getPageImages() {
        return pageImages;
    }

    public void setPageImages(List<BufferedImage> pageImages) {
        this.pageImages = pageImages;
    }

    public void setPageImage(BufferedImage pageAsImage) {
        if (pageImagesIsSet == false) {
            this.pageImages = new ArrayList<>();
            pageImagesIsSet = true;
        }
        this.pageImages.add(this.pageImages.size(), pageAsImage);
    }
}
