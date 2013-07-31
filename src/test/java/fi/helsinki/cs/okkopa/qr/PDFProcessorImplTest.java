package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jpedal.exception.PdfException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PDFProcessorImplTest {

    QRCodeReader reader;
    PDFSplitter splitter;
    PDFProcessorImpl processor;
    private List<ExamPaper> exams;

    @Before
    public void setUp() {
        reader = new QRCodeReader();
        splitter = new PDFSplitter();
        processor = new PDFProcessorImpl(splitter, reader);
    }

    public PDFProcessorImplTest() {
    }

    @Test
    public void readQRCodeFromPDF() throws NotFoundException, IOException, ChecksumException, NotFoundException, FormatException, DocumentException, COSVisitorException, PdfException, fi.helsinki.cs.okkopa.exception.NotFoundException {
        InputStream file = getClass().getResourceAsStream("/pdf/packed2.pdf");

        exams = splitter.splitPdf(file);

        int i = 0;

        for (ExamPaper examPaper : exams) {
            try {
                i++;
                String QRCode = processor.readQRCode(examPaper);
                System.out.println(QRCode);
            } catch (Exception e) {
                System.out.println("virhe " + i);
            }
        }
    }
}