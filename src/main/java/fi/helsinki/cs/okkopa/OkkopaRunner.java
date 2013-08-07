package fi.helsinki.cs.okkopa;

import fi.helsinki.cs.okkopa.database.OkkopaDatabase;
import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.pdfprocessor.PDFProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
import org.jpedal.exception.PdfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private PDFProcessor pDFProcessor;
    private EmailRead server;
    private ExamPaperSender sender;
    private Settings settings;
    private static Logger LOGGER = Logger.getLogger(OkkopaRunner.class.getName());
    private OkkopaDatabase okkopaDatabase;

    @Autowired
    public OkkopaRunner(EmailRead server, ExamPaperSender sender, PDFProcessor pDFProcessor, Settings settings, OkkopaDatabase okkopaDatabase) {
        this.server = server;
        this.sender = sender;
        this.pDFProcessor = pDFProcessor;
        this.settings = settings;
        this.okkopaDatabase = okkopaDatabase;
    }

    @Override
    public void run() {
        try {
            LOGGER.debug("kirjaudutaan sisään");
            server.connect();
            LOGGER.debug("kirjauduttu sisään");
            while (true) {
                LOGGER.debug("Haetan vanhimman viestin liitteet");
                ArrayList<InputStream> attachments = server.getNextMessagesAttachments();
                LOGGER.debug("Vanhimman viestin liitteet haettu");
                if (attachments == null) {
                    LOGGER.info("Ei uusia viestejä.");
                    break;
                }
                for (InputStream inputStream : attachments) {
                    LOGGER.debug("käsitellään liitettä");
                    processAttachment(inputStream);
                    IOUtils.closeQuietly(inputStream);
                }
            }
        } catch (MessagingException | IOException ex) {
            logException(ex);
        } finally {
            server.close();
        }
    }
    
    private void processAttachment(InputStream inputStream) {
        List<ExamPaper> examPapers;
        try {
            examPapers = pDFProcessor.splitPDF(inputStream);
        } catch (DocumentException ex) {
            logException(ex);
            // nothing to process, return
            return;
        } 
        ExamPaper courseInfo = examPapers.get(0);
        try {
            int courseID = getCourseInfo(courseInfo);
            examPapers.remove(courseInfo);
        } catch (NotFoundException ex) {
            logException(ex);
            //LOGGER.log(Level.SEVERE, null, ex);
        }
        
        while(!examPapers.isEmpty()) {
            ExamPaper examPaper = examPapers.remove(0);
            processExamPaper(examPaper);
        }
    }
    
    
    private void processExamPaper(ExamPaper examPaper) {
        try {
            examPaper.setPageImages(pDFProcessor.getPageImages(examPaper));
        } catch (PdfException ex) {
            logException(ex);
            return;
        }
        try {
            examPaper.setQRCodeString(pDFProcessor.readQRCode(examPaper));
        } catch (NotFoundException ex) {
            //Todo: pdf:n tallennus
            logException(ex);
            return;
        }
        
        if (examPaper.getQRCodeString().isEmpty()) {
            //Todo: pdf:n tallennus
            return;           
        }
        try {
            examPaper.setUserid(fetchUserId(examPaper.getQRCodeString()));
        } catch (SQLException | NotFoundException ex) {
            //Todo: rekisteröitymättömien käsittely
        }
        
        //Todo: tähän sähköpostiosoitteen resolvaus / muodostaminen
        examPaper.setEmail(fetchEmail(examPaper.getUserid()));
        

        sendEmail(examPaper);
        saveToTikli(examPaper);
        LOGGER.debug("ExamPaper lähetetty ja tallennettu tikliin");
        return;
    }
    

    public int getCourseInfo(ExamPaper examPaper) throws NotFoundException {
        try {
            examPaper.setPageImages(pDFProcessor.getPageImages(examPaper));
            return Integer.parseInt(examPaper.getQRCodeString());
        } catch (PdfException | NumberFormatException ex) {
            throw new NotFoundException("Course ID not found.");
        }
    }

    private void saveToTikli(ExamPaper examPaper) {
        LOGGER.info("Tässä vaiheessa tallennettaisiin paperit Tikliin");
    }

    private void sendEmail(ExamPaper examPaper) {
        try {
            sender.send(examPaper);
        } catch (MessagingException ex) {
            logException(ex);
        }
    }
    
    private void logException(Exception ex) {
        //Currently just logging exceptions. Should exception handling be in its own class?
        if (settings.getSettings().getProperty("logger.showcompletestack").equals("true")) {
            LOGGER.error(ex.toString(), ex);
        } else {
            LOGGER.error(ex.toString());
        }
    }
    
    private String fetchUserId(String qrcode) throws SQLException, NotFoundException {     
        if (Character.isDigit(qrcode.charAt(0)))
            return okkopaDatabase.getUserID(qrcode); 
        return qrcode;
    }
    
    private String fetchEmail(String userId) {
        //todo: tänne magia, jolla käyttäjätunnus muutetaan sähköpostiosoitteeksi
        //esim: return userId+"@cs.helsinki.fi";
        return "okkopa2.2013@gmail.com";
    }
}