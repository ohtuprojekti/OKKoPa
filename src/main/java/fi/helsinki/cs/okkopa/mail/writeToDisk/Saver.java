package fi.helsinki.cs.okkopa.mail.writeToDisk;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

public interface Saver {

    boolean saveInputStream(InputStream inputStream, String folderPath, String fileName) throws FileAlreadyExistsException;

    ArrayList<File> list(String folderPath);
}
