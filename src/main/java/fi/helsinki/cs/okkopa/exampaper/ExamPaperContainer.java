/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.exampaper;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import java.io.IOException;

/**
 *
 * KESKEN
 */
public interface ExamPaperContainer {
    
    
    public void saveExamPaper(ExamPaper examPaper) throws IOException;
    
    
    public ExamPaper getExamPaper(int position) throws ArrayIndexOutOfBoundsException;
    
    
    public int getAmount();
    
}
