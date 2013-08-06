package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.exampaper.ExamPaperFileContainer;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.jpedal.exception.PdfException;

public class Save implements Saver {

    File saveFile;
    File openFile;
    String folderName;
    private String fileName;

    public Save() throws IOException {
        this.folderName = ""+ System.currentTimeMillis();
        this.fileName = "" + System.currentTimeMillis();
        File folder = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/"+ folderName);
        folder.mkdir();
        //saveFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/fail/exm.pdf");
        openFile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa/src/test/resources/pdf/all.pdf");
        
    }

    @Override
        public void saveToFile() {

        try {
            saveFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(saveFile))) {
            stream.writeBoolean(true);
            stream.writeChars("....");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void list() {
        File f = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa");
        String listOfFiles= "";
        File[] files = f.listFiles();

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
                listOfFiles= files[i].getName();
                System.out.println(listOfFiles);
            }
        }
    }
    private File getResourceFile(String name) throws URISyntaxException {
        return new File(getClass().getResource(folderName+"/"+name).toURI());
    }
    

    @Override
    public void saveExamPaper(ExamPaper examPaper) {
         File savefile;
     
            //savefile = getResourceFile(folderName+"/"+System.currentTimeMillis()+".pdf");
            savefile = new File("/cs/fs/home/tirna/OKKoPa/OKKoPa "+ folderName + fileName);
       
        
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(savefile);
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
         //save.saveToFile();
       // save.delete();
        save.list();
    }
}
