package fi.helsinki.cs.okkopa.qr;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpedal.exception.PdfException;
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
    public List<ExamPaper> splitPDF(InputStream pdfStream) throws DocumentException {
        try {
            return splitter.splitPdf(pdfStream);
        } catch (IOException ex) {
            throw new DocumentException(ex.getMessage());
        } catch (PdfException ex) {
            Logger.getLogger(PDFProcessorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String readQRCode(ExamPaper examPaper) throws NotFoundException {
        Result result = null;
        Exception e = null;
        for (BufferedImage pageImage : examPaper.getPageImages()) {
            try {
                result = reader.readQRCode(pageImage);
            } catch (com.google.zxing.NotFoundException | ChecksumException | FormatException ex) {
                e = ex;
            }
        }
        if (result != null) {
            return result.getText();
        } else {
            throw new fi.helsinki.cs.okkopa.exception.NotFoundException(e.getMessage());
        }
    }
}
