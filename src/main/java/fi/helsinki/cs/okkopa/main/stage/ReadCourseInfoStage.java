package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.CourseInfo;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCourseInfoStage extends Stage<List<ExamPaper>, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(ReadCourseInfoStage.class.getName());
    private ExceptionLogger exceptionLogger;

    @Autowired
    public ReadCourseInfoStage(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(List<ExamPaper> examPapers) {
        ExamPaper courseInfoPage = examPapers.get(0);
        CourseInfo courseInfo = null;
        try {
            courseInfo = getCourseInfo(courseInfoPage);
            // Remove if succesful so that the page won't be processed as
            // a normal exam paper.
            examPapers.remove(courseInfoPage);
        } catch (NotFoundException ex) {
            exceptionLogger.logException(ex);
        }

        // Process all examPapers
        while (!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            // Add course info
            examPaper.setCourseInfo(courseInfo);
            processNextStages(examPaper);
        }
    }

    public CourseInfo getCourseInfo(ExamPaper examPaper) throws NotFoundException {
        // TODO unimplemented
        throw new NotFoundException("Course ID not found.");
    }
}
