package fi.helsinki.cs.okkopa;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender;
import fi.helsinki.cs.okkopa.exception.DocumentException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.qr.ExamPaper;
import fi.helsinki.cs.okkopa.qr.PDFProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private PDFProcessor pDFProcessor;
    
    private EmailRead server;
    private ExamPaperSender sender;

    @Autowired
    public OkkopaRunner(EmailRead server, ExamPaperSender sender, PDFProcessor pDFProcessor) {
        this.server = server;
        this.sender = sender;
        this.pDFProcessor = pDFProcessor;
    }

    @Override
    public void run() {
        try {
            // TEST
            server.connect();
            while (true) {
                ArrayList<InputStream> attachments = server.getNextAttachment();
                if (attachments == null) {
                    System.out.println("Ei lisää viestejä.");
                    break;
                }
                for (InputStream inputStream : attachments) {
                    // PDF to exam papers
                    List<ExamPaper> processPDF = processPdf(inputStream);
                    for (ExamPaper examPaper : processPDF) {
                        sendEmail(examPaper);
                    }
                    IOUtils.closeQuietly(inputStream);
                }
            }
        } catch (NoSuchProviderException ex) {
            // TODO
            System.out.println("ei provideria");
        } catch (MessagingException ex) {
            // TODO
            System.out.println("messaging ex " + ex);
        } catch (IOException ex) {
            // TODO
            System.out.println("io ex");
        } finally {
            server.close();
        }
    }

    private void sendEmail(ExamPaper examPaper) {
        try {
            sender.send(examPaper);
        } catch (MessagingException ex) {
            System.out.println("virhe lähetyksessä" + ex);
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
}