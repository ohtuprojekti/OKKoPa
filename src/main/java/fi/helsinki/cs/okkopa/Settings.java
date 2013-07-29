/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author phemmila
 */

public class Settings {

    public Settings(String fileName) {
        this.settings = readSettingXML(fileName);
    }
    
    private Properties settings;
    
//    
//     /**
//     *Smtp-settings, read from resources/smtpsettings.xml.
//     */
//    public final static Properties SMTPPROPS = readSettingXML("/smtpsettings.xml");
//     /**
//     *Passwords, read from resources/pwdsettings.xml.
//     */
//    
//
//    public final static Properties PWDPROPS = readSettingXML("/pwdsettings.xml");
//   
//    /**
//     *Imap-settings, read from resources/imapsettings.xml
//     */
//   public final static Properties IMAPPROPS = readSettingXML("/imapsettings.xml");
//

    
    public Properties getSettings() {
        return this.settings;
    }
    
    
    private static Properties readSettingXML(String fileName) {
       try {
           Properties currentProps = new Properties();
           InputStream currentStream = Settings.class.getResourceAsStream(fileName);
           currentProps.loadFromXML(currentStream);
           currentStream.close();
           return currentProps;
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
           return null;
       }
       
    }
}
