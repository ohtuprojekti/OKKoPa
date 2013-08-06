/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tirna
 */
public class SaveTest {
    
    public SaveTest() {
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
     * Test of saveToFile method, of class Save.
     */
    @Test
    public void testSaveToFile() throws IOException {
        System.out.println("saveToFile");
        Save instance = new Save();
        instance.saveToFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class Save.
     */
    @Test
    public void testDelete() throws IOException {
        System.out.println("delete");
        Save instance = new Save();
        instance.delete();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class Save.
     */
    @Test
    public void testList() throws IOException {
        System.out.println("list");
        Save instance = new Save();
        instance.list();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveExamPaper method, of class Save.
     */
    @Test
    public void testSaveExamPaper() throws IOException {
        System.out.println("saveExamPaper");
        ExamPaper examPaper = null;
        Save instance = new Save();
        instance.saveExamPaper(examPaper);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

  
}