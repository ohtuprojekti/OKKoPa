/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.exampaper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.io.IOUtils;

/**
 *
 * @author anttkaik
 */
public class ExamPaperFileContainer implements ExamPaperContainer {

    @Override
    public void saveExamPaper(ExamPaper examPaper) throws IOException {
        File savefile = new File("fails/"+System.currentTimeMillis()+".pdf");
        FileOutputStream outputStream = new FileOutputStream(savefile);
        IOUtils.copy(examPaper.getPdfStream(), outputStream);
        outputStream.close();
        examPaper.getPdfStream().close();
 
    }
      
    /**
     * 
     * @param position Position 0 is the oldest
     */
    @Override
    public ExamPaper getExamPaper(int position) throws ArrayIndexOutOfBoundsException {
        return null;
    }
    
    
    @Override
    public int getAmount() {
        return -1;
    }
    
    
}
