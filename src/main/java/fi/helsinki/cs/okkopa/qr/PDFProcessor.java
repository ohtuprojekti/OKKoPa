package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;

public interface PDFProcessor {

    String readQRCode(ExamPaper examPaper) throws Exception;

    /**
     * Converts a PDF-document to a list of images, one per page.
     * 
     * @param pdfStream PDF-file as InputStream.
     * @return
     * @throws IOException If file is not in PDF format.
     * @throws DocumentException If document has odd number of pages.
     * @throws COSVisitorException If something goes wrong with the InputStream 
     * of PDF-file. 
     */
    List<ExamPaper> splitPDF(InputStream pdfStream) throws IOException, DocumentException, COSVisitorException;
    
}
