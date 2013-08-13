/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import fi.helsinki.cs.okkopa.main.Settings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author phemmila
 */
public class OracleConnector {

    private ConnectionSource connectionSource;
    private String url;
    private String pwd;
    private String user;
    private String host;
    private String port;
    private String instance;
        
    @Autowired
    public OracleConnector(Settings settings) {
        this.pwd = settings.getSettings().getProperty("database.oracle.pwd");
        this.user = settings.getSettings().getProperty("database.oracle.user");
        this.host = settings.getSettings().getProperty("database.oracle.host");
        this.port = settings.getSettings().getProperty("database.oracle.port");
        this.instance = settings.getSettings().getProperty("database.oracle.instance");
        this.url = "jdbc:oracle:thin:@"+this.host+":"+this.port+":"+this.instance;        
    }
    
    private void connect() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(url, user, pwd);
        System.out.println(connectionSource.isOpen());
        
        
    }
    
    public static void main(String[] args) {
        try {
            OracleConnector oc = new OracleConnector(new Settings("settings.xml"));
            oc.connect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 

        
    }
    
}
