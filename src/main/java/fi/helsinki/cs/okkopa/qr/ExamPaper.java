package fi.helsinki.cs.okkopa.qr;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

public class ExamPaper {

    private InputStream pdfStream;
    private String QRCodeString;
    private List<BufferedImage> pageImages;

    public void setQRCodeString(String QRCodeString) {
        this.QRCodeString = QRCodeString;
    }

    public String getQRCodeString() {
        return QRCodeString;
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
}
