/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
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
public class SaverTest {
    
    public SaverTest() {
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
     * Test of saveToFile method, of class Saver.
     */
    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");
        Saver instance = new SaverImpl();
        instance.saveToFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveExamPaper method, of class Saver.
     */
    @Test
    public void testSaveExamPaper() {
        System.out.println("saveExamPaper");
        ExamPaper examPaper = null;
        Saver instance = new SaverImpl();
        instance.saveExamPaper(examPaper);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class Saver.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Saver instance = new SaverImpl();
        instance.delete();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class Saver.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Saver instance = new SaverImpl();
        instance.list();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SaverImpl implements Saver {

        public void saveToFile() {
        }

        public void saveExamPaper(ExamPaper examPaper) {
        }

        public void delete() {
        }

        public void list() {
        }
    }
}