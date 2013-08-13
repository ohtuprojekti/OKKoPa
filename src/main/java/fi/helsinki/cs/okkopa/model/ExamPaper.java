package fi.helsinki.cs.okkopa.model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Single exam paper a list of page images and QR code information.
 */
public class ExamPaper {

    private byte[] pdf;
    private String QRCodeString;
    private List<BufferedImage> pageImages;
    private Student student;
    private CourseInfo courseInfo;
    
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
    
    public void setPageImages(List<BufferedImage> pageImages) {
        this.pageImages = pageImages;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
}
