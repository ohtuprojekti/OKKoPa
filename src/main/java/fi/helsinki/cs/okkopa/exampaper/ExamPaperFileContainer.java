/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.exampaper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anttkaik
 */
public class ExamPaperFileContainer implements ExamPaperContainer {

    
    private String folderName;
    private File folder;
    
    
    public ExamPaperFileContainer(String folderName) throws FileNotFoundException {
        this.folderName = folderName;
        try {
            folder = getResourceFile(folderName);
        } catch (URISyntaxException ex) {
            throw new FileNotFoundException("Kansiota "+folderName+" ei löydy");
        }
    }
    
    
    private File getResourceFile(String name) throws URISyntaxException {
        return new File(getClass().getResource(folderName+"/"+name).toURI());
    }
    
    
    @Override
    public void saveExamPaper(ExamPaper examPaper) throws IOException {
        File savefile;
        try {
            savefile = getResourceFile(folderName+"/"+System.currentTimeMillis()+".pdf");
        } catch (URISyntaxException ex) {
            Logger.getLogger(ExamPaperFileContainer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
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
        File file;
        try {
            file = getResourceFile(folderName);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ExamPaperFileContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    @Override
    public int getAmount() {
        return -1;
    }
    
    
}
