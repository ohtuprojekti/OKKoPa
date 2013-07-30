package fi.helsinki.cs.okkopa;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    public Settings(String fileName) {
        this.settings = readSettingXML(fileName);
    }
    
    private Properties settings;

    public Properties getSettings() {
        return this.settings;
    }
    
    
    private Properties readSettingXML(String fileName) {
       try {
           Properties currentProps = new Properties();
           InputStream currentStream = getClass().getResourceAsStream(fileName);
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
