/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import fi.helsinki.cs.okkopa.model.Course;
import fi.helsinki.cs.okkopa.model.Feedback;
import fi.helsinki.cs.okkopa.main.Settings;
import java.sql.SQLException;
import java.util.List;
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
    Dao<Course, Object> courseDao;
    Dao<Feedback, String> feedBack;

    @Autowired
    public OracleConnector(Settings settings) {
        this.pwd = settings.getSettings().getProperty("database.oracle.password");
        this.user = settings.getSettings().getProperty("database.oracle.user");
        this.host = settings.getSettings().getProperty("database.oracle.host");
        this.port = settings.getSettings().getProperty("database.oracle.port");
        this.instance = settings.getSettings().getProperty("database.oracle.instance");
        this.url = "jdbc:oracle:thin:@" + this.host + ":" + this.port + ":" + this.instance;
    }

    private void connect() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(url, user, pwd);
    }

    public boolean courseExists(Course course) throws SQLException {
        try {
            this.connect();
            this.courseDao = DaoManager.createDao(connectionSource, Course.class);
            List<Course> result;
            result = this.courseDao.queryForMatching(course);
            if (result.size() == 1) {
                System.out.println(result.get(0).getName());
                return true;
            }
            else return false;
     
            
        } catch (SQLException ex) {
            this.connectionSource.close();
            throw ex;
        }
    }
    
 /*   public static void main(String[] args) {
        try {
            OracleConnector oc = new OracleConnector(new Settings("settings.xml"));
            System.out.println(oc.courseExists(new Course("581386","S",2000,"L",2)));
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}
