package stories

import fi.helsinki.cs.okkopa.qr.QRCodeReader
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.Settings
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSenderImpl
import fi.helsinki.cs.okkopa.qr.ExamPaper
import org.apache.pdfbox.pdmodel.PDDocument
import fi.helsinki.cs.okkopa.qr.PDFSplitter
import java.security.Security
import com.icegreen.greenmail.util.DummySSLSocketFactory
import javax.mail.internet.MimeMessage
import javax.mail.Multipart
import javax.mail.BodyPart

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
        ExamPaperSender examPaperSender = new ExamPaperSenderImpl(settings);
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
        ExamPaperSender examPaperSender = new ExamPaperSenderImpl(settings);
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