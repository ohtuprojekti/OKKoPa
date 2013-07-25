/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;
import java.io.FileInputStream;
import java.util.Properties;
/**
 *
 * @author phemmila
 */
public class Settings {
     //Settings as "Properties"- objects:
     public final static Properties SMTPPROPS = readSettingXML("smtpsettings.xml");
     public final static Properties PWDPROPS = readSettingXML("pwdsettings.xml");
     public final static Properties IMAPPROPS = readSettingXML("imapsettings.xml");

     
     private static Properties readSettingXML(String fileName) {
       try {
           Properties currentProps = new Properties();
           FileInputStream currentStream = new FileInputStream(fileName);
           currentProps.loadFromXML(currentStream);
           currentStream.close();
           return currentProps;
       }
       catch (Exception e) {
           return null;
       }
       
    }
}
