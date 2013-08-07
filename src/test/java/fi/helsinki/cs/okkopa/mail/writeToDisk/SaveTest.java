/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.Splitter;
import org.jpedal.exception.PdfException;
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

    Save save;
    PDFSplitter splitter;
    List<ExamPaper> papers;
    File folder;
    File saveFile;
    Calendar mydate;
    String fileName;
    public SaveTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        save = new Save();
        splitter = new PDFSplitter();
        papers = new ArrayList<>();
        folder = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/7.7.2013");
       
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of saveToFile method, of class Save.
     */
    /**
     * Test of delete method, of class Save.
     */
    @Test
    public void testDelete() throws IOException {
        System.out.println("delete");

        boolean size = save.list().containsAll(papers);
        save.delete();
        save.delete();
        save.delete();
        save.delete();
        assertTrue(!size);
    }

    /**
     * Test of list method, of class Save.
     */
    @Test
    public void testList() throws IOException {
        System.out.println("list");
        save.list();


    }

    @Test
    public void testFolderIsExist() throws IOException {
        System.out.println("Is folder exist?");
        assertTrue(folder.exists());


    }

    /**
     * Test of saveExamPaper method, of class Save.
     */
    @Test
    public void testSaveExamPaper() throws IOException {

        try {
            papers = splitter.splitToExamPapersWithPDFStreams(new FileInputStream(save.openFile));
        } catch (DocumentException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PdfException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (COSVisitorException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        save.saveExamPaper(papers.get(0));
        Save save2 = new Save();
        save2.saveExamPaper(papers.get(1));
        Save save3 = new Save();
        save3.saveExamPaper(papers.get(5));

        assertTrue(!save.folderName.isEmpty());


    }
}