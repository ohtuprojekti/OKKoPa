package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;

public class PDFProcessorImpl implements PDFProcessor {

    PDFSplitter splitter;
    QRCodeReader reader;

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
    public String readQRCode(ExamPaper examPaper) throws NotFoundException {
        Result result = null;
        NotFoundException e = null;
        for (BufferedImage pageImage : examPaper.getPageImages()) {
            try {
                result = reader.readQRCode(pageImage);
            } catch (NotFoundException ex) {
                e = ex;
            }
        }
        if (result == null) {
            throw e;
        }
        return result.getText();
    }
}
