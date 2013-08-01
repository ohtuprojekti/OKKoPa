package fi.helsinki.cs.okkopa;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import fi.helsinki.cs.okkopa.logger.OKKoPaLoggerTest;
import fi.helsinki.cs.okkopa.qr.PDFProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private PDFProcessor pDFProcessor;
    private EmailRead server;
    private ExamPaperSender sender;    
    private Settings settings;
    private static Logger LOGGER = Logger.getLogger(OkkopaRunner.class.getName());
    
    @Autowired
    public OkkopaRunner(EmailRead server, ExamPaperSender sender, PDFProcessor pDFProcessor, Settings settings) {
        this.server = server;
        this.sender = sender;
        this.pDFProcessor = pDFProcessor;
        this.settings = settings;
    }

    @Override
    public void run() {
        try {
            // TEST
            server.connect();
            
            while (true) {
                ArrayList<InputStream> attachments = server.getNextAttachment();
                if (attachments == null) {
                    LOGGER.info("Ei uusia viestejä.");
                    break;
                }
                for (InputStream inputStream : attachments) {
                    // PDF to exam papers
                    List<ExamPaper> processPDF = processPdf(inputStream);
                    if (processPDF.isEmpty()) {
                        LOGGER.info("Tyhjä lista.");
                        continue;
                    }
                    ExamPaper cover = processPDF.get(0);
                    Integer id = null;
                    try {
                        id = getCoverPageCourseID(cover);
                        processPDF.remove(0);
                    } catch (NotFoundException ex) {
                        LOGGER.info("Ei kansisivua.");
                    }
                    sendEmails(processPDF);
                    if (id != null && settings.getSettings().getProperty("tikli.enable").equals("true")) {
                        saveToTikli(processPDF);
                    
                    }


                    IOUtils.closeQuietly(inputStream);
                }
            }
        } catch (NoSuchProviderException ex) {
            // TODO
            exceptionHandler(ex);
            
        } catch (MessagingException ex) {
            // TODO
            exceptionHandler(ex);
            
        } catch (IOException ex) {
            // TODO
            exceptionHandler(ex);
        } finally {
            server.close();
        }
    }

    public int getCoverPageCourseID(ExamPaper examPaper) throws NotFoundException {
        try {
            return Integer.parseInt(examPaper.getQRCodeString());
        } catch (NumberFormatException ex) {
            throw new NotFoundException("Course ID not found.");
        }
    }

    private void saveToTikli(List<ExamPaper> examPapers) {
        
    }

    private void sendEmails(List<ExamPaper> examPapers) {
        for (ExamPaper examPaper : examPapers) {
            try {
                sender.send(examPaper);
            } catch (MessagingException ex) {
                System.out.println("virhe lähetyksessä" + ex);
            }
        }
    }

    private List<ExamPaper> processPdf(InputStream inputStream) {
        List<ExamPaper> okPapers = new ArrayList<>();
        List<ExamPaper> processPDF = new ArrayList<>();
        try {
            processPDF = pDFProcessor.splitPDF(inputStream);
        } catch (DocumentException ex) {
            System.out.println(ex);
        }
        for (ExamPaper examPaper : processPDF) {
            try {
                examPaper.setQRCodeString(pDFProcessor.readQRCode(examPaper));
                okPapers.add(examPaper);
            } catch (NotFoundException ex) {
                System.out.println("QR code not found " + ex);
            }
        }
        return okPapers;
    }
    
    private void exceptionHandler(Exception ex) {
        //Currently just logging exceptions. Should exception handling be in its own class?
            if (settings.getSettings().getProperty("logger.showcompletestack").equals("true"))
                LOGGER.error(ex.toString(),ex);
            else
                LOGGER.error(ex.toString());
    }
}