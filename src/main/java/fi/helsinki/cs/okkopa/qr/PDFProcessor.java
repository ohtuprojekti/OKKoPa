package fi.helsinki.cs.okkopa.qr;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.InputStream;
import java.util.List;

public interface PDFProcessor {

    /**
     * Takes an ExamPaper and reads a QR code from it.
     * 
     * @param examPaper Input ExamPaper.
     * @return Read QR code as a String.
     * @throws NotFoundException If QR code was not found.
     */
    String readQRCode(ExamPaper examPaper) throws NotFoundException;

    /**
     * 
     *
     * @param pdfStream PDF file as an InputStream
     * @return A list of ExamPapers, one per two pages of the input PDF.
     * @throws DocumentException If document is not in the right format or has
     *                            an odd number of pages.
     */
    List<ExamPaper> splitPDF(InputStream pdfStream) throws DocumentException;
    
}
