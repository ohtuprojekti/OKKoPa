/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.delete;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.mail.writeToDisk.FileSaver;
import fi.helsinki.cs.okkopa.mail.writeToDisk.Saver;
import java.io.File;
import java.util.ArrayList;

/**
 * Removes old unreadble attachments from the disk.
 * File age must be determined in a settings file.
 *
 */
public class ErrorPDFRemover implements Remover {

    private String saveFolder;
    private int saveDays;
    private Saver saver;
    
    
    /**
     * Initializes the object.
     * @param settings Settings that are loaded from a settings file. Settings must contain file age and folder path.
     */
    public ErrorPDFRemover(Settings settings) {
        this.saveFolder = settings.getSettings().getProperty("exampaper.saveunreadablefolder");
        this.saveDays = Integer.parseInt(settings.getSettings().getProperty("exampaper.deleteunreadableafterdays"));
        this.saver = new FileSaver();
        
    }
    
    
    /**
     * Deletes all messages in the specified folder that are older than the specified age in settings.
     */
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