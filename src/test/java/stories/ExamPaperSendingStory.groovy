package stories

import fi.helsinki.cs.okkopa.qr.QRCodeReader
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.Settings
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSender
import fi.helsinki.cs.okkopa.mail.send.ExamPaperSenderImpl
import fi.helsinki.cs.okkopa.qr.ExamPaper
import org.apache.pdfbox.pdmodel.PDDocument

description "Email sending"
 
scenario "Sending an exam paper and then receiving an email", {
 
    given "An exam paper and a mail server",{
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        GreenMail greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
        Properties props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
        ExamPaper examPaper = new ExamPaper();
        PDDocument examPdf = new PDDocument();
        examPaper.setPageImages(extractImages(examPdf));
        examPaper.setPdfStream(getPdfStream(examPdf));
    }
 
    when "Email is sent", {
        
        ExamPaperSender examPaperSender = new ExamPaperSenderImpl();
        examPaperSender.send(null);
    }
 
    then "An email should be received", {
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
    }
}


scenario "Sending an exam paper and then receiving an email with attachment", {
 
    given "An exam paper and a mail server",{
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        GreenMail greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
        Properties props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
    }
 
    when "Email is sent", {
    }
 
    then "An email should be received containing a pdf attachment", {
    }
}