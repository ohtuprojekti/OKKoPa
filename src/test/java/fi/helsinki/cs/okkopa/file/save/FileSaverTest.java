/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.file.save;

import fi.helsinki.cs.okkopa.file.save.FileSaver;
import java.io.File;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkaik
 */
public class FileSaverTest {

    private FileSaver saver;

    public FileSaverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        saver = new FileSaver();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of list method, of class FileSaver.
     */
    @Test
    public void testList() {
        clearDirectory("src/test/resources/savetest");

        try {
            InputStream is = getClass().getResourceAsStream("/pdf/basic_qr.pdf");
            saver.saveInputStream(is, "src/test/resources/savetest", "testipdfvanhempi.pdf");
            is = getClass().getResourceAsStream("/pdf/basic_qr.pdf");
            saver.saveInputStream(is, "src/test/resources/savetest", "testipdfuudempi.pdf");
        } catch (FileAlreadyExistsException ex) {
            fail();
        }

        List<File> files = saver.list("src/test/resources/savetest");
        assertEquals(2, files.size());
        List<String> correctNames = new ArrayList<String>();
        correctNames.add("testipdfvanhempi.pdf");
        correctNames.add("testipdfuudempi.pdf");
        for (int i = 0; i < correctNames.size(); i++) {
            assertTrue(correctNames.contains(files.get(i).getName()));
        }
    }

    private void clearDirectory(String directoryPath) {
        List<File> files = saver.list(directoryPath);
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }

    /**
     * Test of saveExamPaper method, of class FileSaver.
     */
    @Test
    public void testSaveExamPaper() throws Exception {
        clearDirectory("src/test/resources/savetest");
        InputStream is = getClass().getResourceAsStream("/pdf/basic_qr.pdf");
        boolean result = saver.saveInputStream(is, "src/test/resources/savetest", "testipdf.pdf");
        assertTrue(result);
    }
}