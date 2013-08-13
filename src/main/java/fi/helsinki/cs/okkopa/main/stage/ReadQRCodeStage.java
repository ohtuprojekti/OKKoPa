package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.writeToDisk.Saver;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.OkkopaRunner;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.IOUtils;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadQRCodeStage extends Stage<ExamPaper, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(ReadCourseInfoStage.class.getName());
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
        saveErrorFolder = settings.getSettings().getProperty("exampaper.saveunreadablefolder");
        saveOnExamPaperPDFError = Boolean.parseBoolean(settings.getSettings().getProperty("exampaper.saveunreadable"));
    }

    @Override
    public void process(ExamPaper examPaper) {
        try {
            examPaper.setPageImages(pdfProcessor.getPageImages(examPaper));
            examPaper.setQRCodeString(pdfProcessor.readQRCode(examPaper));
        } catch (PdfException | NotFoundException ex) {
            exceptionLogger.logException(ex);
            if (saveOnExamPaperPDFError) {
                try {
                    LOGGER.debug("Tallennetaan virheellistä pdf tiedostoa kansioon " + saveErrorFolder);
                    InputStream stream = new ByteArrayInputStream(examPaper.getPdf());
                    fileSaver.saveInputStream(stream, saveErrorFolder, "" + System.currentTimeMillis() + ".pdf");
                    IOUtils.closeQuietly(stream);
                } catch (FileAlreadyExistsException ex1) {
                    java.util.logging.Logger.getLogger(OkkopaRunner.class.getName()).log(Level.SEVERE, "File already exists", ex1);
                }
            }
            // Don't continue if we couldn't read QR code.
            return;
        }
        processNextStages(examPaper);
    }
}
