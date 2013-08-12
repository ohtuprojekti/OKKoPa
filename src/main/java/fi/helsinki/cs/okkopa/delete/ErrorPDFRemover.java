/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.delete;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.mail.writeToDisk.FileSaver;
import fi.helsinki.cs.okkopa.mail.writeToDisk.Saver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author anttkaik
 */
public class ErrorPDFRemover implements Remover {

    private String saveFolder;
    private int saveDays;
    private Saver saver;
    
    
    public ErrorPDFRemover(Settings settings) {
        this.saveFolder = settings.getSettings().getProperty("exampaper.saveunreadablefolder");
        this.saveDays = Integer.parseInt(settings.getSettings().getProperty("exampaper.deleteunreadableafterdays"));
        this.saver = new FileSaver();
        
    }
    
    
    
    @Override
    public void deleteOldMessages() {
        ArrayList<File> fileList = saver.list(saveFolder);
        if (fileList == null) {
            return;
        };
        for (File pdf : fileList) {
            float fileAgeInDays = (System.currentTimeMillis() - pdf.lastModified()) / (1000*60*60*24);
            if (fileAgeInDays > saveDays) {
                pdf.delete();
            }
        }
    }
    
    
}
