/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.CourseDbModel;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.FeedbackDbModel;
import fi.helsinki.cs.okkopa.model.StudentDbModel;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter;
import java.io.InputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phemmila
 */
public class OracleConnectorTest {
    
    public OracleConnectorTest() {
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
     * Test of getCourseList method, of class OracleConnector.
     */
    @Test
    public void testGetCourseList() throws Exception {
 
    }

    /**
     * Test of courseExists method, of class OracleConnector.
     */
    @Test
    public void testCourseExists() throws Exception {

    }

    /**
     * Test of studentExists method, of class OracleConnector.
     */
    @Test
    public void testStudentExists() throws Exception {

    }

    /**
     * Test of insertFeedBackRow method, of class OracleConnector.
     */
//    @Test
//    public void testInsertFeedBackRow() throws Exception {
//        PDFSplitter splitter = new PDFSplitter();
//        InputStream file = getClass().getResourceAsStream("/pdf/basic_qr.pdf");
//        List<ExamPaper> exampapers = splitter.splitToExamPapersWithPDFStreams(file);
//        CourseDbModel course = new CourseDbModel("581287", "K", 2008, "K",1);
//        FeedbackDbModel feedback = new FeedbackDbModel(course,exampapers.get(0).getPdf(), "012617177");
//        OracleConnector oc = new OracleConnector(new Settings("settings.xml"));
//        oc.insertFeedBackRow(feedback);
//        
//    }
}