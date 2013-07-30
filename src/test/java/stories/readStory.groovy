package stories

import fi.helsinki.cs.okkopa.*
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.GreenMail
import fi.helsinki.cs.okkopa.Settings
import fi.helsinki.cs.okkopa.mail.read.IMAPserver
import fi.helsinki.cs.okkopa.mail.read.MailRead
import java.security.Security
import com.icegreen.greenmail.util.DummySSLSocketFactory
import com.icegreen.greenmail.user.GreenMailUser
GreenMail greenMail
Properties props
MailRead mail;
description "E-mail login"
 
scenario "User can login with valid e-mail/password combination", {
    
    
    given "",{
        ServerSetup setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
        ServerSetup setup2 = new ServerSetup(4011, "localhost", ServerSetup.PROTOCOL_IMAPS);
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
        mail = new MailRead(new Settings("/smtptestsettings.xml"))
        mail.connect()
    }
 
    then "user will be logged in to system", {
        greenMail.stop()
    
    }
}
//scenario "User can,t login with invalid e-mail/password combination", {
// 
//    given "",{
//        setup = new ServerSetup(4012, "localhost", ServerSetup.PROTOCOL_SMTPS);
//        greenMail = new GreenMail(setup); //uses test ports by default
//        greenMail.start();
//       props = (new Settings("/smtptestsettings.xml")).getSettings();
//        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
//        
//      
//        
//       
//    }
// 
//    when "", {
//        MailRead mail = new MailRead(new Settings("src/test/resources/smtptestsettings.xml"))
//        mail.connect()
//        
//    }
// 
//    then "user will not be logged in to system", {
//        greenMail.stop()
//    }
//}