package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.model.FailedEmailDbModel;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides database connection to store e-mails failed while sending.
 */
@Component
public class FailedEmailDAO {

    private Dao<FailedEmailDbModel, String> emailDao;

    /**
     * Sets up the DAO for the FailedEmail table.
     * 
     * @param settings Settings with database configuration in it.
     * @throws SQLException If database connection setup fails.
     */
    @Autowired
    public FailedEmailDAO(OkkopaDatabaseConnectionSource connectionSource) throws SQLException {
        // instantiate the dao
        emailDao = DaoManager.createDao(connectionSource, FailedEmailDbModel.class);

        // creates table if it doesn't exist
        TableUtils.createTableIfNotExists(connectionSource, FailedEmailDbModel.class);
    }

    /**
     * Add a FailedEmail to database.
     * 
     * @param failedEmail FailedEmail to store.
     * @throws SQLException In case of any failure while saving.
     */
    public void addFailedEmail(FailedEmailDbModel failedEmail) throws SQLException {
        emailDao.create(failedEmail);
    }

    /**
     * Lists all FailedEmails from the database.
     * 
     * @return A list of all FailedEmails.
     * @throws SQLException In case of failure with database.
     */
    public List<FailedEmailDbModel> listAll() throws SQLException {
        return emailDao.queryForAll();
    }
}
