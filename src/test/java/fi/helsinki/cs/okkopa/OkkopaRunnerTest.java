/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;

import fi.helsinki.cs.okkopa.database.QRCodeDatabase;
import fi.helsinki.cs.okkopa.ldap.LdapConnector;
import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender;
import fi.helsinki.cs.okkopa.mail.writeToDisk.Saver;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OkkopaRunnerTest {

    private OkkopaRunner runner;
    private EmailRead emailRead;
    private ExamPaperSender examPaperSender;
    private PDFProcessor pdfProcessor;
    private Settings settings;
    private QRCodeDatabase qrCodedatabase;
    private LdapConnector ldapConnecto;
    private Saver saver;
  
  
    public OkkopaRunnerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        emailRead = EasyMock.createMock(EmailRead.class);
        examPaperSender = EasyMock.createMock(ExamPaperSender.class);
        pdfProcessor =EasyMock.createMock(PDFProcessor.class);
        settings = EasyMock.createMock(Settings.class);
        qrCodedatabase = EasyMock.createMock(QRCodeDatabase.class);
        ldapConnecto =EasyMock.createMock(LdapConnector.class);
        saver = EasyMock.createMock(Saver.class);
       runner = new OkkopaRunner(emailRead, examPaperSender, pdfProcessor,settings ,qrCodedatabase,ldapConnecto , saver);
       
    }
    

    @After
    public void tearDown() {
        emailRead= null;
        examPaperSender = null;
        pdfProcessor = null;
        settings = null;
        qrCodedatabase = null;
        ldapConnecto = null;
        saver = null;
    }

    /**
     * Test of run method, of class OkkopaRunner.
     */
    @Test
    public void testRun() throws NoSuchProviderException, MessagingException {
        System.out.println("run");
        emailRead.connect();
        emailRead.close();
         
       
        
        
    }

    /**
     * Test of getCourseInfo method, of class OkkopaRunner.
     */
    @Test
    public void testGetCourseInfo() throws Exception {
        System.out.println("getCourseInfo");
        ExamPaper examPaper = null;
        OkkopaRunner instance = null;
        String expResult = "";
        String result = instance.getCourseInfo(examPaper);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}