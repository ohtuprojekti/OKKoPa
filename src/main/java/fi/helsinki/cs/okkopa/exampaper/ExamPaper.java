package fi.helsinki.cs.okkopa.exampaper;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Single exam paper a list of page images and QR code information.
 */
public class ExamPaper {

    private InputStream splitPdfStream;
    private String QRCodeString;
    private List<BufferedImage> pageImages;
    private InputStream pdfStream;

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
        return pdfStream;
    }

    public void setPdfStream(InputStream pdfStream) {
        this.pdfStream = pdfStream;
    }

    public InputStream getSplitPdfStream() {
        return splitPdfStream;
    }

    public void setSplitPdfStream(InputStream splitPdf) {
        this.splitPdfStream = splitPdf;
    }

    public void setPageImages(List<BufferedImage> pageImages) {
        this.pageImages = pageImages;
    }
}
