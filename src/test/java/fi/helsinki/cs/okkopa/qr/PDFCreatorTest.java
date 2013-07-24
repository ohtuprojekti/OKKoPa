package fi.helsinki.cs.okkopa.qr;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.pdfbox.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class PDFCreatorTest {

    PDFCreator creator;

    @Before
    public void setUp() {
        creator = new PDFCreator();
    }

    @Test
    public void testCreatePDF() throws Exception {
        ArrayList<BufferedImage> pages = new ArrayList<>();
        InputStream barCodeInputStream = getClass().getResourceAsStream("/images/basic_qr-0.png");
        pages.add(ImageIO.read(barCodeInputStream));
        barCodeInputStream = getClass().getResourceAsStream("/images/empty_page.png");
        pages.add(ImageIO.read(barCodeInputStream));
        InputStream inputStream = creator.createPDF(pages);
        File file = new File("test.pdf");
        FileOutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(inputStream, outputStream);
    }
}