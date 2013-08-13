/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import fi.helsinki.cs.okkopa.main.Settings;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author phemmila
 */
public class OracleConnector {

    private ConnectionSource connectionSource;
    
    @Autowired
    public OracleConnector(Settings settings) {

    }
    
}
