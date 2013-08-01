/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jpedal.exception.PdfException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anttkaik
 */
public class PDFProcessingSpeedTest {
    
    PDFSplitter splitter;
    QRCodeReader reader;
    List<ExamPaper> examPapers;
    PDFProcessor pdfProcessor;
    
    @Before
    public void setUp() throws IOException, DocumentException, PdfException {
        splitter = new PDFSplitter();
        reader = new QRCodeReader();
        pdfProcessor = new PDFProcessorImpl(splitter, reader);
        InputStream file = getClass().getResourceAsStream("/pdf/basic_qr.pdf");
        examPapers = pdfProcessor.splitPDF(file);
        List<ExamPaper> examPapers2 = new ArrayList<ExamPaper>();
        List<ExamPaper> examPapers3 = new ArrayList<ExamPaper>();
        List<ExamPaper> examPapers4 = new ArrayList<ExamPaper>();
        Collections.copy(examPapers, examPapers2);
        Collections.copy(examPapers, examPapers3);
        Collections.copy(examPapers, examPapers4);
//        examPapers.addAll(examPapers2);
//        examPapers.addAll(examPapers3);
//        examPapers.addAll(examPapers4);       
    }
    
    @Test
    public void abc() {
        double startTime = System.currentTimeMillis();
        for (ExamPaper examPaper : examPapers) {
            List<BufferedImage> imgs = examPaper.getPageImages();
            System.out.println("----------------");
            System.out.println("images: "+imgs.size());
            boolean foundnull = false;
            for (BufferedImage img : imgs) {
                if (img == null) {
                    System.out.println("null");
                    foundnull = true;
                } else {
                    if (img.getWidth() == 0) {
                        System.out.println("leveys 0");
                    }
                            
                    System.out.println("not null");
                }
            }
            if (foundnull) continue;
            try {
                pdfProcessor.readQRCode(examPaper);
            } catch (NotFoundException ex) {
                System.out.println("qr code not found");
            }
        }
        System.out.println("It took "+(System.currentTimeMillis()-startTime)+
                " to read " + examPapers.size() + " ExamPapers.");
    }
    
}
