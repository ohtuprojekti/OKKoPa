package fi.helsinki.cs.okkopa.file.delete;

import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.file.save.FileSaver;
import fi.helsinki.cs.okkopa.file.save.Saver;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Removes old unreadable attachments from the disk. File age must be determined
 * in a settings file.
 *
 */
@Component
public class ErrorPDFRemover implements Remover {

    private String saveFolder;
    private int saveDays;
    private Saver saver;

    /**
     * Initializes the object.
     *
     * @param settings Settings that are loaded from a settings file. Settings
     * must contain file age and folder path.
     */
    @Autowired
    public ErrorPDFRemover(Settings settings) {
        this.saveFolder = settings.getProperty("exampaper.saveunreadablefolder");
        this.saveDays = Integer.parseInt(settings.getProperty("exampaper.deleteunreadableafterdays"));
        this.saver = new FileSaver();

    }

    /**
     * Deletes all messages in the specified folder that are older than the
     * specified age in settings.
     */
    @Override
    public void deleteOldMessages() {
        ArrayList<File> fileList = saver.list(saveFolder);
        if (fileList == null) {
            return;
        }
        for (File pdf : fileList) {
            float fileAgeInDays = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - pdf.lastModified());
            if (fileAgeInDays > saveDays) {
                pdf.delete();
            }
        }
    }
}
