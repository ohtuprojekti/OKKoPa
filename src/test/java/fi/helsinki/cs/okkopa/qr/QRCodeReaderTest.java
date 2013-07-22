package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QRCodeReaderTest {
    
    QRCodeReader reader;
    
    @Before
    public void setUp() throws Exception {
        reader = new QRCodeReader();
    }
    
    /**
     * Test reading a file with only a single QR code.
     */
    @Test
    public void readASingleQRCode() {
        try {
            InputStream barCodeInputStream = getClass().getResourceAsStream("/images/qr_code_only.png");
            BufferedImage image = ImageIO.read(barCodeInputStream);
            Result result = reader.readQRCode(image);
            assertEquals("http://en.m.wikipedia.org", result.getText());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    /**
     * 
     */
    @Test(expected = NotFoundException.class)
    public void readImageWithoutQRCode() throws NotFoundException {
        BufferedImage image = null;
        try {
            InputStream barCodeInputStream = getClass().getResourceAsStream("/images/qr_code_missing.gif");
            image = ImageIO.read(barCodeInputStream);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        reader.readQRCode(image);
    }
}
