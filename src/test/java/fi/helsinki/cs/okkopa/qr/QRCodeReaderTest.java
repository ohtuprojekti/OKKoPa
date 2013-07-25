package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QRCodeReaderTest {

    QRCodeReader reader;

    @Before
    public void setUp() throws IOException {
        reader = new QRCodeReader();
    }

    @Test(expected = NotFoundException.class)
    public void readEmptyPage() throws IOException, NotFoundException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/empty_page.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        reader.readQRCode(image);
    }

    /**
     * Test reading an exam paper with only a single QR code.
     */
    @Test
    public void readASingleQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/basic_qr-0.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        assertEquals("asperhee", reader.readQRCode(image).getText());
    }

    /**
     * Test reading an exam paper without QR code.
     */
    @Test(expected = NotFoundException.class)
    public void readExamPaperWithoutQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/empty_page.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        reader.readQRCode(image);
    }

    /**
     * Test reading an exam paper with two same QR codes on same side.
     */
    @Test
    public void readTwoQRCodesSameSide() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/two_same.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        assertEquals("asperhee", reader.readQRCode(image).getText());
    }

    /**
     * Test reading an exam paper with two and a half QR codes on same side.
     */
    @Test(expected = NotFoundException.class)
    public void readTwoAndAHalfQRCode() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/two_half_upsidedown.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        reader.readQRCode(image);
    }

    /**
     * Test reading an exam paper with two different QR codes on same side.
     */
    @Test(expected = NotFoundException.class)
    public void readTwoDifferentQRCodesSameSide() throws NotFoundException, IOException {
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/two_different.png");
        BufferedImage image = ImageIO.read(barCodeInputStream);
        reader.readQRCode(image);
    }
}
