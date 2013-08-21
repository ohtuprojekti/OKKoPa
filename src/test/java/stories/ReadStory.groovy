package stories

import fi.helsinki.cs.okkopa.*
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.mail.read.EmailRead
import java.security.Security
import com.icegreen.greenmail.util.DummySSLSocketFactory
import com.icegreen.greenmail.user.GreenMailUser
import fi.helsinki.cs.okkopa.pdfprocessor.PDFSplitter
import fi.helsinki.cs.okkopa.model.ExamPaper
import fi.helsinki.cs.okkopa.main.Settings
import fi.helsinki.cs.okkopa.mail.read.EmailReadImpl
import fi.helsinki.cs.okkopa.mail.send.EmailSenderImpl
import fi.helsinki.cs.okkopa.mail.send.EmailSender
GreenMail greenMail
Properties props
EmailRead mail;
description "E-mail login"
 
scenario "User can login with valid e-mail/password combination", {
    
    
    given "",{
        ServerSetup setup = new ServerSetup(4014, "localhost", ServerSetup.PROTOCOL_SMTPS);
        ServerSetup setup2 = new ServerSetup(4015, "localhost", ServerSetup.PROTOCOL_IMAPS);
        ServerSetup[] setups = new ServerSetup[2];
        setups[0] = setup;
        setups[1] = setup2;
        greenMail = new GreenMail(setups); //uses test ports by default
        GreenMailUser user = greenMail.setUser("okkopa@localhost.com", "okkopa", "soooosecret");
        greenMail.start();
         props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
        
       
    }
 
    when "", {
        mail = new EmailReadImpl(new Settings("smtptestsettings.xml"))
        mail.connect()
    }
 
    then "user will be logged in to system", {
        mail.close();
        true.shouldBe true;
        greenMail.stop()
    
    }
}
scenario "User can,t login with invalid e-mail/password combination", {
 
    given "",{
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        ServerSetup setup2 = new ServerSetup(4011, "localhost", ServerSetup.PROTOCOL_IMAPS);
        ServerSetup[] setups = new ServerSetup[2];
        setups[0] = setup;
        setups[1] = setup2;
        greenMail = new GreenMail(setups); //uses test ports by default
        GreenMailUser user = greenMail.setUser("o@localhost.com", "okkopa", "sot");
        greenMail.start();
         props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
       
    }
 
    when "", {
        mail = new EmailReadImpl(new Settings("/smtptestsettings.xml"))
        //mail.connect()
        
    }
 
    then "user will not be logged in to system", {
        ensureThrows(Exception){
            mail.connect()
        }
        mail.close()
        greenMail.stop()
    }
}

scenario "attachment..", {
    
    
    given "",{
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        ServerSetup setup2 = new ServerSetup(4011, "localhost", ServerSetup.PROTOCOL_IMAPS);
        ServerSetup[] setups = new ServerSetup[2];
        setups[0] = setup;
        setups[1] = setup2;
        greenMail = new GreenMail(setups); //uses test ports by default
        GreenMailUser user = greenMail.setUser("okkopa@localhost.com", "okkopa", "soooosecret");
        greenMail.start();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
        PDFSplitter splitter = new PDFSplitter();
        InputStream is = new FileInputStream("src/test/resources/pdf/basic_qr.pdf");
        List<ExamPaper> examPapers = splitter.splitPdf(is);
        examPaper = examPapers.get(0);
        
        
       
    }
 
    when "", {
        EmailSender emailSender = new EmailSenderImpl(new Settings("/smtptestsettings.xml"));
        examPaperSender.send(examPaper);
        greenMail.waitForIncomingEmail(5000, 1);
        mail = new EmailReadImpl(new Settings("/smtptestsettings.xml"))
        mail.connect()
    }
 
    then "", {
        ArrayList<InputStream> attachments = mail.getNextAttachment()
        attachments.shouldNotBe null
        mail.close();
        greenMail.stop()
    
    }
}
