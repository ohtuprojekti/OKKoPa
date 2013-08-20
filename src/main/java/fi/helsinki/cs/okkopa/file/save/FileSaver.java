package fi.helsinki.cs.okkopa.file.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.nio.file.FileAlreadyExistsException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Create a folder per day(if we run it everyday) and save files to folder.
 * lists all files by date and delete file.
 */
@Component
public class FileSaver implements Saver {
    
    private static final Logger LOGGER = Logger.getLogger(FileSaver.class.getName());

    public FileSaver() {
    }

    /**
     * Lists files in a specific folder
     *
     * @return sorted list of files in folder
     */
    @Override
    public ArrayList<File> list(String folderPath) {
        File f = new File(folderPath);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }

        ArrayList<File> list = new ArrayList();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                list.add(files[i]);

            }
        }

        return list;
    }

    /**
     * Save ExamPapers to local disk.
     *
     * @param examPaper,
     */
    @Override
    public boolean saveInputStream(InputStream inputStream, String folderPath, String fileName) throws FileAlreadyExistsException {
        if (inputStream == null || folderPath == null || fileName == null) {
            LOGGER.warn("saveInputStream got null parameter. Returning false!");
            return false;
        }

        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            LOGGER.warn("Failed to create folder " + folderPath + ".");
            return false;
        }
        File file = new File(folderPath + "/" + fileName);
        if (file.exists()) {
            throw new FileAlreadyExistsException("File " + folderPath + "/" + fileName + " already exists.");
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            LOGGER.error(ex.toString(), ex);
            return false;
        }
        return true;
    }
}
