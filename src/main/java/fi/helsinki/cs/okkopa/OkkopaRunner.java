package fi.helsinki.cs.okkopa;

import com.google.zxing.NotFoundException;
import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.mail.read.MailRead;
import fi.helsinki.cs.okkopa.mail.send.OKKoPaAuthenticatedMessage;
import fi.helsinki.cs.okkopa.mail.send.OKKoPaMessage;
import fi.helsinki.cs.okkopa.qr.DocumentException;
import fi.helsinki.cs.okkopa.qr.ExamPaper;
import fi.helsinki.cs.okkopa.qr.PDFProcessor;
import fi.helsinki.cs.okkopa.qr.PDFProcessorImpl;
import fi.helsinki.cs.okkopa.qr.PDFSplitter;
import fi.helsinki.cs.okkopa.qr.QRCodeReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private PDFProcessor pDFProcessor;
    
    @Autowired
    private EmailRead server;

    public OkkopaRunner() {
        pDFProcessor = new PDFProcessorImpl(new PDFSplitter(), new QRCodeReader());
    }

    @Override
    public void run() {
        try {
            // TEST
            System.out.println("1. merkki");
            server.connect();
            System.out.println("2. merkki");
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
            Properties props = Settings.SMTPPROPS;
            Properties salasanat = Settings.PWDPROPS;
            OKKoPaMessage msg = new OKKoPaAuthenticatedMessage("okkopa.2013@gmail.com", "okkopa2.2013@gmail.com", props, "okkopa2.2013", salasanat.getProperty("smtpPassword"));
            msg.addPDFAttachment(examPaper.getPdfStream(), "liite.pdf");
            msg.setSubject("testipdf");
            msg.setText("katso liite  " + examPaper.getQRCodeString());
            msg.send();
            System.out.println("lähetetty");
        } catch (MessagingException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private List<ExamPaper> processPdf(InputStream inputStream) {
        List<ExamPaper> okPapers = new ArrayList<>();
        List<ExamPaper> processPDF = new ArrayList<>();
        try {
            processPDF = pDFProcessor.splitPDF(inputStream);
        } catch (IOException ex) {
            // Not pdf-format
            System.out.println("Not pdf-format");
        } catch (DocumentException ex) {
            // Odd number of pages
            System.out.println("Odd number of pages");
        } catch (COSVisitorException ex) {
            Logger.getLogger(OkkopaRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (ExamPaper examPaper : processPDF) {
            try {
                examPaper.setQRCodeString(pDFProcessor.readQRCode(examPaper));
                okPapers.add(examPaper);
            } catch (Exception ex) {
                // QR code not found
                System.out.println("QR code not found");
            }
        }
        return okPapers;
    }
}