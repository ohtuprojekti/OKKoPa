package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PDFProcessorImpl implements PDFProcessor {

    PDFSplitter splitter;
    QRCodeReader reader;
    @Autowired
    public PDFProcessorImpl(PDFSplitter splitter, QRCodeReader reader) {
        this.splitter = splitter;
        this.reader = reader;
    }

    /**
     *
     * @param pdfStream
     * @return
     * @throws IOException If file is not in PDF format.
     * @throws DocumentException If document has odd number of pages.
     */
    @Override
    public List<ExamPaper> splitPDF(InputStream pdfStream) throws IOException, DocumentException, COSVisitorException {
        return splitter.splitPdf(pdfStream);
    }

    @Override
    public String readQRCode(ExamPaper examPaper) throws Exception {
        Result result = null;
        Exception e = null;
        for (BufferedImage pageImage : examPaper.getPageImages()) {
            try {
                result = reader.readQRCode(pageImage);
                System.out.println(result);
            } catch (NotFoundException ex) {
                System.out.println("ei löytynyt");
                e = ex;
            } catch (ChecksumException ex) {
                e = ex;
            } catch (FormatException ex) {
                e = ex;
            }
        }
        if (result == null) {
            throw e;
        }
        return result.getText();
    }
}
