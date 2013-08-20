package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.main.BatchDetails;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.InputStream;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SplitPDFStage extends Stage<InputStream, List<ExamPaper>> {

    private static final Logger LOGGER = Logger.getLogger(SplitPDFStage.class.getName());
    PDFProcessor pdfProcessor;
    ExceptionLogger exceptionLogger;
    private BatchDetails batch;

    @Autowired
    public SplitPDFStage(PDFProcessor pdfProcessor, ExceptionLogger exceptionLogger, BatchDetails batch) {
        this.batch = batch;
        this.pdfProcessor = pdfProcessor;
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(InputStream in) {
        List<ExamPaper> examPapers;
        // Split PDF to ExamPapers (2 pages per each).
        try {
            examPapers = pdfProcessor.splitPDF(in);
            LOGGER.debug("PDF jaettu osiin.");
            batch.setTotalPages(examPapers.size());
        } catch (DocumentException ex) {
            // Errors: bad PDF-file, odd number of pages.
            exceptionLogger.logException(ex);
            return;
        }
        processNextStages(examPapers);
    }
}
