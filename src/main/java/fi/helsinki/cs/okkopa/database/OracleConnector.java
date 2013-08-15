/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import fi.helsinki.cs.okkopa.model.CourseDbModel;
import fi.helsinki.cs.okkopa.model.FeedbackDbModel;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.StudentDbModel;
import java.sql.SQLException;
import java.util.Calendar;
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
    private int yearOffset;
    Dao<CourseDbModel, Object> courseDbModel;
    Dao<FeedbackDbModel, String> feedbackDbModel;
    Dao<StudentDbModel, Object> studentDbModel;

    @Autowired
    public OracleConnector(Settings settings) {
        this.pwd = settings.getProperty("database.oracle.password");
        this.user = settings.getProperty("database.oracle.user");
        this.host = settings.getProperty("database.oracle.host");
        this.port = settings.getProperty("database.oracle.port");
        this.instance = settings.getProperty("database.oracle.instance");
        this.yearOffset = Integer.parseInt(settings.getProperty("database.oracle.showcoursesforyears"));

        this.url = "jdbc:oracle:thin:@" + this.host + ":" + this.port + ":" + this.instance;
    }

    private void connect() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(url, user, pwd);
    }

    public List<CourseDbModel> getCourseList() throws SQLException {
        try {
            this.courseDbModel = DaoManager.createDao(connectionSource, CourseDbModel.class);
            int startYear, endYear;
            endYear = Calendar.getInstance().get(Calendar.YEAR);
            startYear = endYear - this.yearOffset;
            QueryBuilder<CourseDbModel, Object> queryBuilder = this.courseDbModel.queryBuilder();
            PreparedQuery prepQuery = queryBuilder.where().between("LUKUVUOSI", (Integer) startYear, (Integer) endYear).prepare();
            this.connectionSource.close();
            return this.courseDbModel.query(prepQuery);
        } catch (SQLException ex) {
            this.connectionSource.close();
            throw ex;
        }
    }

    public boolean courseExists(CourseDbModel course) throws SQLException {
        try {
            this.connect();
            this.courseDbModel = DaoManager.createDao(connectionSource, CourseDbModel.class);
            List<CourseDbModel> result;
            result = this.courseDbModel.queryForMatching(course);
            if (result.size() == 1) {
                System.out.println(result.get(0).getName());
                this.connectionSource.close();
                return true;
            } else {
                this.connectionSource.close();
                return false;
            }

        } catch (SQLException ex) {
            this.connectionSource.close();
            throw ex;
        }
    }

    public boolean studentExists(StudentDbModel student) throws SQLException {
        try {
            this.connect();
            this.studentDbModel = DaoManager.createDao(connectionSource, StudentDbModel.class);
            List<StudentDbModel> result;
            result = this.studentDbModel.queryForMatching(student);
            if (result.size() == 1) {
                System.out.println(result.get(0).getStudentNumber());
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            this.connectionSource.close();
            throw ex;
        }
    }

    public void insertFeedBackRow(FeedbackDbModel newRow) throws SQLException {
        try {
            this.connect();
            this.feedbackDbModel = DaoManager.createDao(connectionSource, FeedbackDbModel.class);
            if (this.feedbackDbModel.create(newRow) != 1) {
                this.connectionSource.close();
                throw new SQLException("Rows inserted <> 1");
            }
            this.connectionSource.close();

        } catch (SQLException ex) {
            this.connectionSource.close();
            throw ex;
        }
    }

    /*public static void main(String[] args) {
     try {
     OracleConnector oc = new OracleConnector(new Settings("settings.xml"));
     //    System.out.println(oc.courseExists(new CourseDbModel("581386","S",2000,"L",2)));
     System.out.println("Should be true:"+oc.studentExists(new StudentDbModel("011442521")));
     System.out.println("Should be false:"+oc.studentExists(new StudentDbModel("-")));
     System.out.println(oc.getCourseList().size());
     } catch (SQLException | IOException ex) {
     System.out.println(ex.getMessage());
     }
     }*/
}
