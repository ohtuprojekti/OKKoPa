/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.InputStream;
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
    PDFProcessor pdfProcessor;

    @Before
    public void setUp() {
        splitter = new PDFSplitter();
        reader = new QRCodeReader();
        pdfProcessor = new PDFProcessorImpl(splitter, reader);
    }
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    @Test
    public void testAllSpeeds() throws DocumentException, PdfException, NotFoundException {
        double splittingTime = System.currentTimeMillis();
        InputStream file = getClass().getResourceAsStream("/pdf/massive.pdf");
        List<ExamPaper> examPapers = pdfProcessor.splitPDF(file);
        splittingTime = System.currentTimeMillis() - splittingTime;
        double readingTime = System.currentTimeMillis();
        int paperAmount = examPapers.size();
        long memorySum = 0;
        while (!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            examPaper.setPageImages(pdfProcessor.getPageImages(examPaper));
            examPaper.setQRCodeString(pdfProcessor.readQRCode(examPaper));
            Runtime runtime = Runtime.getRuntime();
            // SLOW BUT LOW MEMORY
//            runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();
            memorySum += bytesToMegabytes(memory);
        }
        readingTime = System.currentTimeMillis() - readingTime;

        System.out.println("It took " + splittingTime + "ms to split " + paperAmount + " ExamPapers.");
        System.out.println("It took " + readingTime + "ms to read " + paperAmount + " ExamPapers.");
        System.out.println("It took a total of " + (splittingTime + readingTime) + " milliseconds.");
        System.out.println("It took an average of " + (splittingTime + readingTime)/paperAmount + " milliseconds per examPaper");
        System.out.println("Used average " + memorySum / paperAmount + "MB of memory per examPaper.");
    }
}