package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.file.save.Saver;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.IOUtils;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadQRCodeStage extends Stage<ExamPaper, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(ReadQRCodeStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private Saver fileSaver;
    private String saveErrorFolder;
    private boolean saveOnExamPaperPDFError;
    private PDFProcessor pdfProcessor;

    @Autowired
    public ReadQRCodeStage(Saver fileSaver, PDFProcessor pdfProcessor,
            ExceptionLogger exceptionLogger, Settings settings) {
        this.exceptionLogger = exceptionLogger;
        this.fileSaver = fileSaver;
        this.pdfProcessor = pdfProcessor;
        saveErrorFolder = settings.getProperty("exampaper.saveunreadablefolder");
        saveOnExamPaperPDFError = Boolean.parseBoolean(settings.getProperty("exampaper.saveunreadable"));
    }

    @Override
    public void process(ExamPaper examPaper) {
        try {
            examPaper.setPageImages(pdfProcessor.getPageImages(examPaper));
            examPaper.setQRCodeString(pdfProcessor.readQRCode(examPaper));
        } catch (PdfException | NotFoundException ex) {
            exceptionLogger.logException(ex);
            LOGGER.debug("QR-koodia ei pystytty lukemaan.");
            if (saveOnExamPaperPDFError) {
                try {
                    InputStream stream = new ByteArrayInputStream(examPaper.getPdf());
                    fileSaver.saveInputStream(stream, saveErrorFolder, "" + System.currentTimeMillis() + ".pdf");
                    IOUtils.closeQuietly(stream);
                    LOGGER.debug("Tallennettin virheellinen PDF.");
                } catch (FileAlreadyExistsException ex1) {
                    exceptionLogger.logException(ex);
                }
            }
            // Don't continue if we couldn't read QR code.
            return;
        }
        processNextStages(examPaper);
    }
}
