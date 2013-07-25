/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
/**
 *
 * @author phemmila
 */
public class Settings {

     /**
     *Smtp-settings, read from resources/smtpsettings.xml.
     */
    public final static Properties SMTPPROPS = readSettingXML("/smtpsettings.xml");
     /**
     *Passwords, read from resources/pwdsettings.xml.
     */
    

    public final static Properties PWDPROPS = readSettingXML("/pwdsettings.xml");
   
    /**
     *Imap-settings, read from resources/imapsettings.xml
     */
    public final static Properties IMAPPROPS = readSettingXML("/imapsettings.xml");


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
