package fi.helsinki.cs.okkopa.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Single exam paper a list of page images and QR code information.
 */
public class ExamPaper {

    private byte[] pdf;
    private String QRCodeString;
    private List<BufferedImage> pageImages;
    private InputStream pdfStream;
    private Student student;
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Student getStudent() {
        return this.student;
    }
    
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
    
    public InputStream getPdf() {
        return new ByteArrayInputStream(pdf);
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public void setPageImages(List<BufferedImage> pageImages) {
        this.pageImages = pageImages;
    }
}
