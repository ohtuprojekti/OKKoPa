/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;
import java.util.Properties;
import java.io.FileInputStream;


/**
 *
 * @author phemmila
 */
public class SettingsReader {
    
   public static void main(String[] args) {
     //  ConfigReader cf = new ConfigReader();
       readSettingsXML();
   }
   
   protected static void readSettingsXML() {

       try {
           
           //Load imap-settings:
           FileInputStream imapFileStream = new FileInputStream("imapsettings.xml");
           Properties imapProps = new Properties();
           imapProps.loadFromXML(imapFileStream);
           imapFileStream.close();
           
           //Set imap values:
           Settings.IMAPDEFAULTFOLDER = imapProps.getProperty("imapDefaultFolder");
           Settings.IMAPHOSTNAME = imapProps.getProperty("imapHostName");
           Settings.IMAPPORT = imapProps.getProperty("imapPort");
           Settings.IMAPUSERNAME = imapProps.getProperty("imapUserName");
           Settings.IMAPPASSWORD = imapProps.getProperty("imapPassword");
           
           //Load smtp-settings:
           FileInputStream smtpFileStream = new FileInputStream("smtpsettings.xml");
           Properties smtpProps = new Properties();
           smtpProps.loadFromXML(smtpFileStream);
           smtpFileStream.close();
           
           //set smtp values:
           Settings.SMTPHOSTNAME = smtpProps.getProperty("smtpHostName");
           Settings.SMTPPORT = smtpProps.getProperty("smtpPort");
           Settings.SMTPUSERNAME = smtpProps.getProperty("smtpUserName");
           Settings.SMTPPASSWORD = smtpProps.getProperty("smtpPassword");
           Settings.SMTPAUTHENTICATION = smtpProps.getProperty("smtpAuthentication");
           Settings.SMTPSTARTTLS = smtpProps.getProperty("smtpStartTLS");
           
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }    
   }

   
}
