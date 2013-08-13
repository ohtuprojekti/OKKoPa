package fi.helsinki.cs.okkopa.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages and reads XML files.
 */
public class Settings {

    public Settings(String fileName) throws FileNotFoundException, IOException {
        this.settings = readSettingXML(fileName);
        
        try {
            this.settings.putAll(readSettingXML("passwords.xml"));
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    private Properties settings;

    public Properties getSettings() {
        return this.settings;
    }

    private Properties readSettingXML(String fileName) throws FileNotFoundException, IOException {
        Properties currentProps = new Properties();

        try {
            InputStream currentStream = getClass().getResourceAsStream("/" + fileName);
            currentProps.loadFromXML(currentStream);
            currentStream.close();
            return currentProps;
        } catch (Exception e) {
            FileInputStream fis = new FileInputStream(fileName);
            currentProps.loadFromXML(fis);
            fis.close();
            return currentProps;
        }

    }
}
