package fi.helsinki.cs.okkopa.exampaper;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Single exam paper a list of page images and QR code information.
 */
public class ExamPaper {

    private String QRCodeString;
    private List<BufferedImage> pageImages;

    public String getQRCodeString() {
        return QRCodeString;
    }
    
    public void setQRCodeString(String QRCodeString) {
        this.QRCodeString = QRCodeString;
    }

    public List<BufferedImage> getPageImages() {
        return pageImages;
    }

    public void addPageImage(BufferedImage pageAsImage) {
        if (pageImages == null) {
            this.pageImages = new ArrayList<>();
        }
        this.pageImages.add(pageAsImage);
    }
    
    public InputStream getPdfStream() {
        return null;
    }
}
