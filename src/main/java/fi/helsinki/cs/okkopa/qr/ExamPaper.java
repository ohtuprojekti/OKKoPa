package fi.helsinki.cs.okkopa.qr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ExamPaper {
    private List<BufferedImage> pages;
    private String QRCodeString;
    
    ExamPaper() {
        pages = new ArrayList<>();
    }
    
    public void addPage(BufferedImage page) {
        pages.add(page);
    }
}
