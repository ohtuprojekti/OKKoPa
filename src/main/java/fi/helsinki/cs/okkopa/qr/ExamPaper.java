package fi.helsinki.cs.okkopa.qr;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class ExamPaper {
    private List<Image> pages;
    private String QRCodeString;
    
    ExamPaper() {
        pages = new ArrayList<>();
    }
    
    public void addPage(Image page) {
        pages.add(page);
    }
}
