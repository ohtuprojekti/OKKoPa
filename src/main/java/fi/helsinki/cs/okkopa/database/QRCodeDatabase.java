package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.QRCode;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QRCodeDatabase {

    private Dao<QRCode, String> qrCodeDao;
    private ConnectionSource connectionSource;

    @Autowired
    public QRCodeDatabase(Settings settings) throws SQLException {
        String databaseUrl = settings.getProperty("database.h2.url");
        String username = settings.getProperty("database.h2.user");
        String password = settings.getProperty("database.h2.password");

        // create a connection source to our database
        connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

        // instantiate the dao
        qrCodeDao = DaoManager.createDao(connectionSource, QRCode.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, QRCode.class);
    }

    public String getUserID(String qrcodeString) throws SQLException, NotFoundException {
        QRCode qrCode = qrCodeDao.queryForId(qrcodeString);
        if (qrCode == null) {
            throw new NotFoundException();
        }
        return qrCode.getUserId();
    }

    boolean addQRCode(String qrCodeString) throws SQLException {
        QRCode qrCode = new QRCode(qrCodeString, "");

        if (!qrCodeDao.idExists(qrCodeString)) {
            qrCodeDao.createIfNotExists(qrCode);
            return true;
        }
        return false;
    }

    boolean addUSer(String qrCodeString, String UserId) throws SQLException {
        QRCode qrCode = qrCodeDao.queryForId(qrCodeString);

        if (qrCode.getUserId().equals("")) {
            qrCode = new QRCode(qrCodeString, UserId);
            qrCodeDao.update(qrCode);
            return true;
        }
        return false;
    }

    void closeConnectionSource() throws SQLException {
        connectionSource.close();
    }
}
