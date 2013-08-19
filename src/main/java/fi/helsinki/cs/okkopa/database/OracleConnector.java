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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides a connection to Kurki Oracle database.
 */
public class OracleConnector {

    private static Logger LOGGER = Logger.getLogger(OracleConnector.class.getName());
    private ConnectionSource connectionSource;
    private String url;
    private String pwd;
    private String user;
    private String host;
    private String port;
    private String instance;
    private int yearOffset;
    private Dao<CourseDbModel, Object> courseDbModel;
    private Dao<FeedbackDbModel, String> feedbackDbModel;
    private Dao<StudentDbModel, Object> studentDbModel;

    /**
     *
     * @param settings
     */
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

    /**
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(url, user, pwd);
    }

    /**
     *
     */
    public void disconnect() {
        try {
            this.connectionSource.close();
        } catch (SQLException ex) {
            LOGGER.error("Could not disconnect from Oracle DB (Kurki).");
        }
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public List<CourseDbModel> getCourseList() throws SQLException {
        this.courseDbModel = DaoManager.createDao(connectionSource, CourseDbModel.class);
        int startYear, endYear;
        endYear = Calendar.getInstance().get(Calendar.YEAR);
        startYear = endYear - this.yearOffset;
        QueryBuilder<CourseDbModel, Object> queryBuilder = this.courseDbModel.queryBuilder();
        PreparedQuery prepQuery = queryBuilder.where().between("LUKUVUOSI", (Integer) startYear, (Integer) endYear).prepare();
        return this.courseDbModel.query(prepQuery);
    }


    /**
     *
     * @param course
     * @return
     * @throws SQLException
     */
    public boolean courseExists(CourseDbModel course) throws SQLException {
        this.courseDbModel = DaoManager.createDao(connectionSource, CourseDbModel.class);
        List<CourseDbModel> result;
        result = this.courseDbModel.queryForMatching(course);
        if (result.size() == 1) {
            System.out.println(result.get(0).getName());
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param student
     * @return
     * @throws SQLException
     */
    public boolean studentExists(StudentDbModel student) throws SQLException {
        this.studentDbModel = DaoManager.createDao(connectionSource, StudentDbModel.class);
        List<StudentDbModel> result;
        result = this.studentDbModel.queryForMatching(student);
        if (result.size() == 1) {
            System.out.println(result.get(0).getStudentNumber());
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param newRow
     * @throws SQLException
     */
    public void insertFeedBackRow(FeedbackDbModel newRow) throws SQLException {
        this.feedbackDbModel = DaoManager.createDao(connectionSource, FeedbackDbModel.class);
        if (this.feedbackDbModel.create(newRow) != 1) {
            throw new SQLException("Rows inserted <> 1");
        } LOGGER.debug("Palaute rivi lisätty tikliin");
    }

}
