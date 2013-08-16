/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.file.delete.ErrorPDFRemover;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class DeleteOldErrorPDFsStageTest {
    private ErrorPDFRemover errorPDFRemoverMock;
    private DeleteOldErrorPDFsStage deleteOldErrorPDFsStage;
    private Stage nextSatageMock;
    private ExamPaper examPaperMock;
    
    public DeleteOldErrorPDFsStageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        errorPDFRemoverMock = mock(ErrorPDFRemover.class);
        nextSatageMock = mock(Stage.class);
         examPaperMock = mock(ExamPaper.class);
        deleteOldErrorPDFsStage = new DeleteOldErrorPDFsStage(errorPDFRemoverMock);
         deleteOldErrorPDFsStage.setNext(nextSatageMock);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class DeleteOldErrorPDFsStage.
     */
    @Test
    public void testProcess() {
        deleteOldErrorPDFsStage.process(examPaperMock);
       verify(errorPDFRemoverMock,times(1)).deleteOldMessages();
       verify(nextSatageMock, times(1)).process(null);
    }
}