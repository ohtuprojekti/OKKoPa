package fi.helsinki.cs.okkopa;

import com.google.zxing.NotFoundException;
import fi.helsinki.cs.okkopa.mail.send.OKKoPaAuthenticatedMessage;
import fi.helsinki.cs.okkopa.mail.send.OKKoPaMessage;
import fi.helsinki.cs.okkopa.qr.DocumentException;
import fi.helsinki.cs.okkopa.qr.ExamPaper;
import fi.helsinki.cs.okkopa.qr.PDFProcessor;
import fi.helsinki.cs.okkopa.qr.PDFProcessorImpl;
import fi.helsinki.cs.okkopa.qr.PDFSplitter;
import fi.helsinki.cs.okkopa.qr.QRCodeReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.IOUtils;

public class OkkopaRunner implements Runnable {

    PDFProcessor pDFProcessor;

    public OkkopaRunner() {
        pDFProcessor = new PDFProcessorImpl(new PDFSplitter(), new QRCodeReader());
    }

    @Override
    public void run() {
        // TEST
        List<InputStream> attachments = new ArrayList<>();
        try {
            FileInputStream is = new FileInputStream("src/test/resources/pdf/all.pdf");
            attachments.add(is);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        // TEST

        for (InputStream inputStream : attachments) {
            // PDF to exam papers
            List<ExamPaper> processPDF = processPdf(inputStream);
            for (ExamPaper examPaper : processPDF) {
                sendEmail(examPaper);
            }
            IOUtils.closeQuietly(inputStream);
        }
    }

    private void sendEmail(ExamPaper examPaper) {
        try {
            Properties props = Settings.SMTPPROPS;
            Properties salasanat = Settings.PWDPROPS;
            OKKoPaMessage msg = new OKKoPaAuthenticatedMessage("okkopa.2013@gmail.com", "okkopa.2013@gmail.com", props, "okkopa.2013", salasanat.getProperty("smtpPassword"));
            msg.addPDFAttachment(examPaper.getPdfStream(), "liite.pdf");
            msg.setSubject("testipdf");
            msg.setText("katso liite");
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
        List<ExamPaper> processPDF = null;
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
                System.out.println(examPaper.getQRCodeString());
                okPapers.add(examPaper);
            } catch (NotFoundException ex) {
                // QR code not found
                System.out.println("QR code not found");
            }
        }
        System.out.println("loopattu");
        return okPapers;
    }
}