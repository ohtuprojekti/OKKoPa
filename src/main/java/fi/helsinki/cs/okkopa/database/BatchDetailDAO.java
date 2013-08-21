package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.BatchDbModel;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchDetailDAO {

    private Dao<BatchDbModel, String> batchDbDao;

    @Autowired
    public BatchDetailDAO(OkkopaDatabaseConnectionSource connectionSource) throws SQLException {
        batchDbDao = DaoManager.createDao(connectionSource, BatchDbModel.class);
        TableUtils.createTableIfNotExists(connectionSource, BatchDbModel.class);

    }

    public void addBatchDetails(BatchDbModel batchInfo) throws SQLException {
        batchDbDao.createIfNotExists(batchInfo);
    }
    
    public BatchDbModel getBatchDetails(String id) throws SQLException, NotFoundException {
        BatchDbModel batchDb = batchDbDao.queryForId(id);
        if (batchDb == null) {
            throw new NotFoundException();
        }
        return batchDb;
    }
}
