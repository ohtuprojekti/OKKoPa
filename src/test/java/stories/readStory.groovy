package stories

import fi.helsinki.cs.okkopa.*
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.Settings
import fi.helsinki.cs.okkopa.mail.read.IMAPserver
import fi.helsinki.cs.okkopa.mail.read.MailRead
ServerSetup setup
GreenMail greenMail
Properties props

description "E-mail login"
 
scenario "User can login with valid e-mail/password combination", {
 
    given "",{
       setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
         props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
        
       
    }
 
    when "", {
       MailRead mail = new MailRead(new Settings("src/test/resources/smtptestsettings.xml"))
        mail.connect()
    }
 
    then "user will be logged in to system", {
    
    }
}
scenario "User can,t login with invalid e-mail/password combination", {
 
    given "",{
        setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        greenMail = new GreenMail(setup); //uses test ports by default
        greenMail.start();
       props = (new Settings("src/test/resources/smtptestsettings.xml")).getSettings();
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        
      
        
       
    }
 
    when "", {
        MailRead mail = new MailRead(new Settings("src/test/resources/smtptestsettings.xml"))
        mail.connect()
        
    }
 
    then "user will not be logged in to system", {
    }
}