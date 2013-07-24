package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFProcessor {

    PDFSplitter splitter;
    QRCodeReader reader;
    PDFCreator creator;

    public PDFProcessor(PDFSplitter splitter, QRCodeReader reader, PDFCreator creator) {
        this.splitter = splitter;
        this.reader = reader;
        this.creator = creator;
    }

    public List<ExamPaper> processPDF(InputStream pdfStream) {
        ArrayList<ExamPaper> papers = new ArrayList<>();
        try {
            List<BufferedImage> splitPdf = splitter.splitPdf(pdfStream);
            ExamPaper paper = new ExamPaper();
            for (int i = 0; i < splitPdf.size(); i++) {
                if (i % 2 == 0 && i != 0) {
                    if (paper.getQRCodeString() != null) {
                        papers.add(paper);
                    } else {
                        // TODO no qr code
                    }
                    paper = new ExamPaper();
                }
                try {
                    Result result = reader.readQRCode(splitPdf.get(i));
                    paper.setQRCodeString(result.getText());
                } catch (NotFoundException ex) {
                    // TODO no qr code - not needed
                }
            }
        } catch (IOException ex) {
            // TODO wrong document type
        } catch (DocumentException ex) {
            // TODO odd number of pages
        }
        return papers;
    }
    
    public List<ExamPaper> createPDFs(List<ExamPaper> papers) {
        ArrayList<ExamPaper> pdfpapers = new ArrayList<>();
        for (ExamPaper examPaper : papers) {
             
        }
        
        return pdfpapers;
    }
}
