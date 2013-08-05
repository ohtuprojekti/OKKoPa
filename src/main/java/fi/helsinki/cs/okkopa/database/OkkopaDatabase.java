package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.model.QRCode;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaDatabase {

    private Dao<QRCode, String> qrcodeDao;
    private ConnectionSource connectionSource;
    
    @Autowired
    public OkkopaDatabase(Settings settings) throws SQLException {
        String databaseUrl = settings.getSettings().getProperty("database.url");
        // create a connection source to our database
        connectionSource =
                new JdbcConnectionSource(databaseUrl);

        // instantiate the dao
        qrcodeDao =
                DaoManager.createDao(connectionSource, QRCode.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, QRCode.class);
    }

    String getUserID(String qrcodeString) throws SQLException, NotFoundException {
        QRCode qrCode = qrcodeDao.queryForId(qrcodeString);
        if (qrCode == null)
            throw new NotFoundException();
        return qrCode.getUserId();
    }
    
    void addQRCode(String QRCode, String UserId) throws SQLException {
        QRCode qrCode = new QRCode(QRCode, UserId);
        qrcodeDao.createIfNotExists(qrCode);
        
    }
    
    void closeConnectionSource() throws SQLException {
        connectionSource.close();
    }
}
