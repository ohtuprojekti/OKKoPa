package fi.helsinki.cs.okkopa.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages and reads XML files.
 */
public class Settings extends Properties {

    public Settings(String fileName) throws FileNotFoundException, IOException {
        Properties props = readSettingXML(fileName);

        try {
            props.putAll(readSettingXML("passwords.xml"));
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        this.putAll(props);
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
