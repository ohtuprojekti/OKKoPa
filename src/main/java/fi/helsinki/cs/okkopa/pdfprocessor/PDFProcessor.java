package fi.helsinki.cs.okkopa.pdfprocessor;

import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import org.jpedal.exception.PdfException;

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
     * Converts a PDF-document to a list of images, one per page.
     *
     * @param pdfStream PDF file as an InputStream
     * @return A list of ExamPapers, one per two pages of the input PDF.
     * @throws DocumentException If document is not in the right format or has
     * an odd number of pages.
     */
    List<ExamPaper> splitPDF(InputStream pdfStream) throws DocumentException;

    /**
     * Converts PDF-document pages (single exam paper) from input stream to a
     * list of images.
     *
     * @param examPaper Input ExamPaper.
     * @return A list of one PDF's pages converted to images.
     * @throws PdfException if error occurs when creating a PDF or converting to
     * images.
     */
    List<BufferedImage> getPageImages(ExamPaper examPaper) throws PdfException;
}
