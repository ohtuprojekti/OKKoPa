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
           
           //Set imap values:
           Settings.IMAPDEFAULTFOLDER = imapProps.getProperty("imapDefaultFolder");
           Settings.IMAPHOSTNAME = imapProps.getProperty("imapHostName");
           Settings.IMAPPORT = imapProps.getProperty("imapPort");
           Settings.IMAPUSERNAME = imapProps.getProperty("imapUserName");

           
           //Load smtp-settings:
           FileInputStream smtpFileStream = new FileInputStream("smtpsettings.xml");
           Properties smtpProps = new Properties();
           smtpProps.loadFromXML(smtpFileStream);
           
           //set smtp values:
           Settings.SMTPHOSTNAME = smtpProps.getProperty("smtpHostName");
           Settings.SMTPPORT = smtpProps.getProperty("smtpPort");
           Settings.SMTPUSERNAME = smtpProps.getProperty("smtpUserName");

           Settings.SMTPAUTHENTICATION = smtpProps.getProperty("smtpAuthentication");
           Settings.SMTPSTARTTLS = smtpProps.getProperty("smtpStartTLS");
           
           //Load passwords:
           FileInputStream pwdFileStream = new FileInputStream("passwords.xml");
           Properties pwdProps = new Properties();
           pwdProps.loadFromXML(pwdFileStream);
 
           Settings.SMTPPASSWORD = pwdProps.getProperty("smtpPassword");
           Settings.IMAPPASSWORD = pwdProps.getProperty("imapPassword");
           
           //Close FileInputStreams:
           imapFileStream.close();
           smtpFileStream.close();
           pwdFileStream.close();
         
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }    
   }

   
}