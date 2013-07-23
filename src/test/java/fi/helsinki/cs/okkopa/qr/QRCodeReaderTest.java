package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QRCodeReaderTest {

    ExamPaper paper;
    QRCodeReader reader;

    @Before
    public void setUp() throws IOException {
        paper = new ExamPaper();
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/empty_page.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        paper.getPages().add(image);

        reader = new QRCodeReader();
    }

    /**
     * Test reading an exam paper with only a single QR code.
     */
    @Test
    public void readASingleQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/basic_qr-0.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        paper.getPages().add(image);
        reader.readQRCode(paper);
        assertEquals("asperhee", paper.getResult().getText());
    }

    /**
     *
     */
    @Test(expected = NotFoundException.class)
    public void readExamPaperWithoutQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/empty_page.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        paper.getPages().add(image);
        reader.readQRCode(paper);
    }

    @Test
    public void readTwoQRCodes() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/three_page-0.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        paper.getPages().add(image);
        reader.readQRCode(paper);
        assertEquals("asperhee", paper.getResult().getText());
    }

    @Test(expected = NotFoundException.class)
    public void readTwoHalfQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/two_half_upsidedown.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        paper.getPages().add(image);
        reader.readQRCode(paper);
    }
    
    
//       TO DO test for two different codes on same page  
}
