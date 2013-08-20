package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.MissedExamDbModel;
import fi.helsinki.cs.okkopa.model.QRCodeDbModel;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides database connection to store generated QR-code strings
 * (anonymous labels).
 */
@Component
public class QRCodeDAO {

    private Dao<QRCodeDbModel, String> qrCodeDao;

    /**
     *
     * @param settings
     * @throws SQLException
     */
    @Autowired
    public QRCodeDAO(OkkopaDatabaseConnectionSource connectionSource) throws SQLException {
        // instantiate the dao
        qrCodeDao = DaoManager.createDao(connectionSource, QRCodeDbModel.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, QRCodeDbModel.class);
    }

    /**
     *
     * @param qrcodeString
     * @return
     * @throws SQLException
     * @throws NotFoundException
     */
    public String getUserID(String qrcodeString) throws SQLException, NotFoundException {
        QRCodeDbModel qrCode = qrCodeDao.queryForId(qrcodeString);
        if (qrCode == null) {
            throw new NotFoundException();
        }
        return qrCode.getUserId();
    }

    boolean addQRCode(String qrCodeString) throws SQLException {
        QRCodeDbModel qrCode = new QRCodeDbModel(qrCodeString, null);

        if (!qrCodeDao.idExists(qrCodeString)) {
            qrCodeDao.create(qrCode);
            return true;
        }
        return false;
    }

    boolean addUSer(String qrCodeString, String UserId) throws SQLException {
        QRCodeDbModel qrCode = qrCodeDao.queryForId(qrCodeString);

        if (qrCode.getUserId() == null) {
            qrCode = new QRCodeDbModel(qrCodeString, UserId);
            qrCodeDao.update(qrCode);
            return true;
        }
        return false;
    }


}
