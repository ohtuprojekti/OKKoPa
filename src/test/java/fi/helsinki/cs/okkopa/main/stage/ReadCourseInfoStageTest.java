package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReadCourseInfoStageTest {

    private ReadCourseInfoStage readCourseInfoStage;
    private ExceptionLogger exceptionLoggerMock;
    private ExamPaper examPaperMock;
    private List<ExamPaper> examPaper;

    public ReadCourseInfoStageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCourseInfo method, of class ReadCourseInfoStage.
     */
    public void testGetCourseInfo() throws Exception {

    }
}
