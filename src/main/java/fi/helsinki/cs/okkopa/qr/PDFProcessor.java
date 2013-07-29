/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;

/**
 *
 * @author heha
 */
public interface PDFProcessor {

    String readQRCode(ExamPaper examPaper) throws Exception;

    /**
     *
     * @param pdfStream
     * @return
     * @throws IOException If file is not in PDF format.
     * @throws DocumentException If document has odd number of pages.
     */
    List<ExamPaper> splitPDF(InputStream pdfStream) throws IOException, DocumentException, COSVisitorException;
    
}
