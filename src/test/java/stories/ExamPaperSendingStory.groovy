package stories

import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.main.Settings
import fi.helsinki.cs.okkopa.mail.send.EmailSenderImpl

import org.apache.pdfbox.pdmodel.PDDocument
import java.security.Security
import com.icegreen.greenmail.util.DummySSLSocketFactory
import javax.mail.internet.MimeMessage
import javax.mail.Multipart
import javax.mail.BodyPart
import fi.helsinki.cs.okkopa.model.ExamPaper
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter
import fi.helsinki.cs.okkopa.mail.send.EmailSender

description "Email sending"
 
scenario "Sending an exam paper and then receiving an email", {
 
    GreenMail greenMail;
    ServerSetup setup;
    ExamPaper examPaper;
    Settings settings;
    
    given "An exam paper and a mail server",{
        setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
        settings = new Settings("smtptestsettings.xml");
        (new Security()).setProperty("ssl.SocketFactory.provider", (new DummySSLSocketFactory()).class.getName());
        
        PDFSplitter splitter = new PDFSplitter();
        InputStream is = new FileInputStream("src/test/resources/pdf/basic_qr.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(is);
        examPaper = examPapers.get(0);
    }
 
    when "Email is sent", {
        EmailSender emailSender = new EmailSenderImpl(settings);
        examPaperSender.send(examPaper);
    }
 
    then "An email should be received", {
        greenMail.waitForIncomingEmail(5000, 1).shouldBe true;
        greenMail.stop();
    }
}


scenario "Sending an exam paper and then receiving an email with attachment", {
 
    GreenMail greenMail;
    ServerSetup setup;
    ExamPaper examPaper;
    Settings settings;
    
    given "An exam paper and a mail server",{
        setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
        settings = new Settings("/smtptestsettings.xml");
        (new Security()).setProperty("ssl.SocketFactory.provider", (new DummySSLSocketFactory()).class.getName());

        PDFSplitter splitter = new PDFSplitter();
        InputStream is = new FileInputStream("src/test/resources/pdf/basic_qr.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(is);
        examPaper = examPapers.get(0);
    }
 
    when "Email is sent", {
        EmailSender emailSender = new EmailSenderImpl(settings);
        examPaperSender.send(examPaper);
    }
 
    then "An email should be received containing a pdf attachment", {
        greenMail.waitForIncomingEmail(5000, 1).shouldBe true;
        MimeMessage[] messages = greenMail.getReceivedMessages();
        MimeMessage message = messages[0];
        Multipart multipart = (Multipart) message.getContent();
        BodyPart bodypart = multipart.getBodyPart(1);
        String contentType = bodypart.getContentType();
        contentType.contains("application/pdf").shouldBe true;
        greenMail.stop();
        
    }
}