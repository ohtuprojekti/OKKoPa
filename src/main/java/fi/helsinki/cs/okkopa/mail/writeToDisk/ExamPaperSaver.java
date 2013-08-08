
package fi.helsinki.cs.okkopa.mail.writeToDisk;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *Create a folder per day(if we run it everyday) and save files to folder. lists  all files by date
 *and delete file.
 */
public class ExamPaperSaver {

    File saveFile;
    String folderName;
    private Calendar mydate;

    public ExamPaperSaver(String folderName) {
        this.folderName = folderName;
    }

//
//    /**
//     *delete the youngest file.
//     */
//    public void delete() {
//        //saveFile = new File("/cs/fs/home/anttkaik/NetBeansProjects/OKKoPa/" + folderName + "/" + fileName + ".pdf");
//        if (saveFile.delete()) {
//            System.out.println(saveFile.getName() + " is deleted!");
//        } else {
//            System.out.println("Delete operation is failed.");
//        }
//
//
//    }
//
//    /**
//     * Sort all of files in folder by lastModified date..
//     *
//     * @return sorted list of files in folder
//     */
//    public ArrayList<File> list() {
//        File f = new File(folderName + "/");
//        File[] files = f.listFiles();
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
//        ArrayList<File> list = new ArrayList();
//        for (int i = 0; i < files.length; i++) {
//
//            if (files[i].isFile()) {
//                list.add(files[i]);
//               
//            }
//        }
//        
//        System.out.println(list);
//        return list;
//      
//    }
//    
//    
//    private String folderName() {
//        mydate = Calendar.getInstance();
//        mydate.setTimeInMillis(System.currentTimeMillis());
//        return this.folderName+"/"+mydate.get(Calendar.DAY_OF_MONTH) + "." + mydate.get(Calendar.MONTH) + "." + mydate.get(Calendar.YEAR);
//    }
//    
//    
//
//    
//    
//    
//    /**
//     *Save ExamPapers to local disk. 
//     *
//     * @param examPaper,
//     */
//    public void saveExamPaper(ExamPaper examPaper) {
//        File dateFolder = new File(folderName());
//                //new File(folderName());
//        if (!dateFolder.exists()) {
//            System.out.println("Luodaan kansiota "+ dateFolder.getAbsolutePath());
//            System.out.println(dateFolder.mkdirs());
//        }
//        String fileName = "" + mydate.get(Calendar.HOUR_OF_DAY) + ":" + mydate.get(Calendar.MINUTE) + ":" + mydate.get(Calendar.SECOND) + ":" + mydate.get(Calendar.MILLISECOND);
//        saveFile = new File(dateFolder.getAbsolutePath() + "/" + fileName + ".pdf");
//        FileOutputStream outputStream = null;
//        try {
//            outputStream = new FileOutputStream(saveFile);
//            IOUtils.copy(examPaper.getPdf(), outputStream);       
//            outputStream.close();
//            examPaper.getPdf().close();
//        } catch (IOException ex) {
//            Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
