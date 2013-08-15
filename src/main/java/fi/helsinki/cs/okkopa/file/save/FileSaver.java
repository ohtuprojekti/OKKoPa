package fi.helsinki.cs.okkopa.file.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.FileAlreadyExistsException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

/**
 * Create a folder per day(if we run it everyday) and save files to folder.
 * lists all files by date and delete file.
 */
@Component
public class FileSaver implements Saver {

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
//        Arrays.sort(files, new Comparator<File>() {
//            @Override
//            public int compare(File o1, File o2) {
//
//                if (((File) o2).lastModified() > ((File) o1).lastModified()) {
//                    return -1;
//                } else if (((File) o2).lastModified() < ((File) o1).lastModified()) {
//                    return +1;
//                } else {
//                    return 0;
//                }
//            }
//        });
        ArrayList<File> list = new ArrayList();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                list.add(files[i]);

            }
        }

        System.out.println(list);
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
            Logger.getLogger(FileSaver.class.getName()).log(Level.WARNING, "saveInputStream got null parameter. Returning false!");
            return false;
        }

        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            Logger.getLogger(FileSaver.class.getName()).log(Level.WARNING, "Failed to create folder " + folderPath + ".");
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
            Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
//    public static void main(String[] args) {
//        File f = new File("fails/testi.txt");
//        System.out.println(f.mkdirs());
//    }
    //testiä
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        Save save = new Save();
//        PDFSplitter splitter = new PDFSplitter();
//        List<ExamPaper> papers = null;
//        try {
//            papers = splitter.splitToExamPapersWithPDFStreams(new FileInputStream(save.openFile));
//        } catch (DocumentException ex) {
//            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (PdfException ex) {
//            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (COSVisitorException ex) {
//            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        save.saveExamPaper(papers.get(0));
//        Save save2 = new Save();
//        save2.saveExamPaper(papers.get(1));
//        Save save3 = new Save();
//        save3.saveExamPaper(papers.get(5));
//
//        save.list();
//        save.delete();
//
//    }
}
