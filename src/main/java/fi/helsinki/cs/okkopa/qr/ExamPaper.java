package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ExamPaper {
    private List<BufferedImage> pages;
    private Result result;

    
    ExamPaper() {
        pages = new ArrayList<>();
    }
    
    public List getPages() {
        return pages;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
}
