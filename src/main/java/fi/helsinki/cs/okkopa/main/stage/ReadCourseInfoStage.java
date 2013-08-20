package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.CourseInfo;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.util.List;
import org.apache.log4j.Logger;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCourseInfoStage extends Stage<List<ExamPaper>, ExamPaper> {

    private static final Logger LOGGER = Logger.getLogger(ReadCourseInfoStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private PDFProcessor pdfProcessor;

    @Autowired
    public ReadCourseInfoStage(PDFProcessor pDFProcessor, ExceptionLogger exceptionLogger) {
        this.pdfProcessor = pDFProcessor;
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(List<ExamPaper> examPapers) {
        ExamPaper courseInfoPage = examPapers.get(0);
        CourseInfo courseInfo = null;
        try {
            courseInfoPage.setPageImages(pdfProcessor.getPageImages(courseInfoPage));
            courseInfoPage.setQRCodeString(pdfProcessor.readQRCode(courseInfoPage));
            courseInfo = getCourseInfo(courseInfoPage);
            // Remove if succesful so that the page won't be processed as
            // a normal exam paper.
            examPapers.remove(0);
            LOGGER.debug("Kurssi-info luettu onnistuneesti.");
        } catch (PdfException | NotFoundException ex) {
            exceptionLogger.logException(ex);
        }

        // Process all examPapers
        while (!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            // Add course info (doesn't matter if null)
            examPaper.setCourseInfo(courseInfo);
            processNextStages(examPaper);
        }
    }

    public CourseInfo getCourseInfo(ExamPaper examPaper) throws NotFoundException {
        String[] fields = examPaper.getQRCodeString().split(":");
        LOGGER.debug("Kurssi-info luettu: " + examPaper.getQRCodeString());
        try {
            return new CourseInfo(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3], Integer.parseInt(fields[4]));
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
