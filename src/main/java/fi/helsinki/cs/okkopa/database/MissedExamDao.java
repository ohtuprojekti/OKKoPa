package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.model.MissedExamDbModel;
import fi.helsinki.cs.okkopa.model.QRCodeDbModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MissedExamDao {
    
    private Dao<MissedExamDbModel, Void> missedExamDao;
    
    @Autowired
    public MissedExamDao(OkkopaDatabaseConnectionSource connectionSource) throws SQLException {
        missedExamDao = DaoManager.createDao(connectionSource, MissedExamDbModel.class);
        TableUtils.createTableIfNotExists(connectionSource, MissedExamDbModel.class);
    }
    
    
    public void addMissedExam(String anonymousCode) throws SQLException {
        QRCodeDbModel qrCode = new QRCodeDbModel(anonymousCode, null);
        MissedExamDbModel missedExam = new MissedExamDbModel(qrCode);
        missedExamDao.create(missedExam);
    }
    
    public List<Date> getMissedExams(String anonymousCode) throws SQLException {      
        QueryBuilder<MissedExamDbModel, Void> queryBuilder = missedExamDao.queryBuilder();
        PreparedQuery prepQuery = queryBuilder.where().eq("qrCode", anonymousCode).prepare();
        
        List<MissedExamDbModel> missedExams = missedExamDao.query(prepQuery);
        List<Date> result = new ArrayList<Date>();
        for (MissedExamDbModel model : missedExams) {
            result.add(model.getDate());
        }
        return result;
    }
}
