package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.model.FailedEmail;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FailedEmailDatabase {

    private Dao<FailedEmail, String> emailDao;
    private ConnectionSource connectionSource;

    @Autowired
    public FailedEmailDatabase(Settings settings) throws SQLException {
        String databaseUrl = settings.getSettings().getProperty("database.h2.url");
        String username = settings.getSettings().getProperty("database.h2.user");
        String password = settings.getSettings().getProperty("database.h2.password");

        // create a connection source to our database
        connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

        // instantiate the dao
        emailDao = DaoManager.createDao(connectionSource, FailedEmail.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, FailedEmail.class);
    }

    void closeConnectionSource() throws SQLException {
        connectionSource.close();
    }

    public void addFailedEmail(String email, String filename) throws SQLException {
        FailedEmail failedEmail = new FailedEmail();
        failedEmail.setFilename(filename);
        failedEmail.setReceiverEmail(email);
        emailDao.create(failedEmail);
    }
    
    public List<FailedEmail> listAll() throws SQLException {
        return emailDao.queryForAll();
    }
}
