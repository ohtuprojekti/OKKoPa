package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.main.Settings;
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
        String databaseUrl = settings.getProperty("database.h2.url");
        String username = settings.getProperty("database.h2.user");
        String password = settings.getProperty("database.h2.password");

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

    public void addFailedEmail(FailedEmail failedEmail) throws SQLException {
        emailDao.create(failedEmail);
    }

    public List<FailedEmail> listAll() throws SQLException {
        return emailDao.queryForAll();
    }
}
