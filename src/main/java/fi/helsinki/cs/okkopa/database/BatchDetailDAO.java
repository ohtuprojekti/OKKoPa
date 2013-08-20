/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.BatchDbModel;
import java.sql.SQLException;

/**
 *
 * @author phemmila
 */
public class BatchDetailDAO {

    private Dao<BatchDbModel, String> batchDbDao;

    public BatchDetailDAO(OkkopaDatabaseConnectionSource connectionSource) throws SQLException {
        batchDbDao = DaoManager.createDao(connectionSource, BatchDbModel.class);
        TableUtils.createTableIfNotExists(connectionSource, BatchDbModel.class);

    }

    public BatchDbModel getBatchDetails(String id) throws SQLException, NotFoundException {
        BatchDbModel batchDb = batchDbDao.queryForId(id);
        if (batchDb == null) {
            throw new NotFoundException();
        }
        return batchDb;
    }
}
