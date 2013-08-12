/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.delete;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.mail.writeToDisk.FileSaver;
import fi.helsinki.cs.okkopa.mail.writeToDisk.Saver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class ErrorPDFRemoverTest {
    
    private Remover remover;
    private Saver saver;
    
        
    public ErrorPDFRemoverTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        Settings settings = new Settings("deletesettings.xml");
        remover = new ErrorPDFRemover(settings);
        saver = new FileSaver();
        
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    private void clearDirectory(String directoryPath) {
        List<File> files = saver.list(directoryPath);
        if (files == null) return;
        for (File file : files) {
            file.delete();
        }
    }
    

    /**
     * Test of deleteOldMessages method, of class ErrorPDFRemover.
     */
    @Test
    public void testDeleteOldMessages() throws FileAlreadyExistsException {
        clearDirectory("src/test/resources/errorpdfs");
        saver.saveInputStream(getClass().getResourceAsStream("/pdf/all.pdf"), "src/test/resources/errorpdfs", System.currentTimeMillis() +".pdf");
        ArrayList<File> list = saver.list("src/test/resources/errorpdfs");
        assertEquals(1, list.size());
        remover.deleteOldMessages();
        list = saver.list("src/test/resources/errorpdfs");
        assertEquals(0, list.size());
    }
}