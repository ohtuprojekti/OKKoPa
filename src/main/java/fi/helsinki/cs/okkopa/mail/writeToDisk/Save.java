package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jpedal.exception.PdfException;

/**
 *
 *
 */
public class Save implements Saver {

    File saveFile;
    File openFile;
    String folderName;
    private String fileName;
    private Calendar mydate;

    public Save() throws IOException {
        folderName();
        File folder = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/" + folderName);
        this.fileName = "" + mydate.get(Calendar.HOUR_OF_DAY) + ":" + mydate.get(Calendar.MINUTE) + ":" + mydate.get(Calendar.SECOND) + ":" + mydate.get(Calendar.MILLISECOND);
        if (!folder.exists()) {
            folder.mkdir();
        }
        saveFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/" + folderName + "/" + fileName + ".pdf");
        openFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/src/test/resources/pdf/all.pdf");

    }

    @Override
    public void delete() {
        if (saveFile.delete()) {
            System.out.println(saveFile.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }


    }

    @Override
    public ArrayList list() {
        File f = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/" + folderName + "/");
        String listOfFiles = "";
        File[] files = f.listFiles();
        ArrayList<String> list = new ArrayList();

        Arrays.sort(files, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                if (((File) o2).lastModified() > ((File) o1).lastModified()) {
                    return -1;
                } else if (((File) o2).lastModified() < ((File) o1).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }
        });

        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                listOfFiles = files[i].getName();
                list.add(listOfFiles);
               
            }
        }
        System.out.println(list);
        return list;
      
    }

    @Override
    public void saveExamPaper(ExamPaper examPaper) {


//           saveFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/"+ folderName+"/"+fileName+".pdf");

        // saveFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa "+ folderName + fileName);
//        String fileName = ""+System.currentTimeMillis();
//        File saveFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/" + folderName() + "/" + fileName + ".pdf");

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(saveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            IOUtils.copy(examPaper.getPdfStream(), outputStream);
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            examPaper.getPdfStream().close();
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String folderName() {
        mydate = Calendar.getInstance();
        mydate.setTimeInMillis(System.currentTimeMillis());
        folderName = mydate.get(Calendar.DAY_OF_MONTH) + "." + mydate.get(Calendar.MONTH) + "." + mydate.get(Calendar.YEAR);
        return folderName;
    }

    //testiä
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Save save = new Save();
        PDFSplitter splitter = new PDFSplitter();
        List<ExamPaper> papers = null;
        try {
            papers = splitter.splitToExamPapersWithPDFStreams(new FileInputStream(save.openFile));
        } catch (DocumentException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PdfException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (COSVisitorException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        save.saveExamPaper(papers.get(0));
        Save save2 = new Save();
        save2.saveExamPaper(papers.get(1));
        Save save3 = new Save();
        save3.saveExamPaper(papers.get(5));

        save.list();
        save.delete();

    }
}
