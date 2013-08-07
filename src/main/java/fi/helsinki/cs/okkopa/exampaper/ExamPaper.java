package fi.helsinki.cs.okkopa.exampaper;

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
    private String email, userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
