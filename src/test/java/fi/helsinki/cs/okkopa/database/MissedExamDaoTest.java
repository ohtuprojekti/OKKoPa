
package fi.helsinki.cs.okkopa.database;

import fi.helsinki.cs.okkopa.main.Settings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MissedExamDaoTest {
    private static Settings settings;
    private OkkopaDatabaseConnectionSource connectionSource;
    private MissedExamDao missedExamDao;
    private QRCodeDAO qrCodeDao;
    
    public MissedExamDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException {
        settings = new Settings("databasetestsettings.xml");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException, FileNotFoundException, IOException {
        connectionSource = new OkkopaDatabaseConnectionSource(settings);
        missedExamDao = new MissedExamDao(connectionSource);
        qrCodeDao = new QRCodeDAO(connectionSource);
        qrCodeDao.addQRCode("0kkopa142921400");
        qrCodeDao.addUser("0kkopa142921400", "ktunnus");

        qrCodeDao.addQRCode("0kkopa143562150");
      
    }
    
    @After
    public void tearDown() {
        connectionSource.closeQuietly();
    }


    @Test
    public void testAddMissedExam() throws Exception {
        missedExamDao.addMissedExam("0kkopa143562150");
        assertTrue(true);
        
    }
 
    @Test
    public void testGet0MissedExams() throws Exception {
        List<Date> missDates = missedExamDao.getMissedExams("0kkopa143562150");
        assertEquals(0, missDates.size());
        missedExamDao.addMissedExam("0kkopa143562150");
        missDates = missedExamDao.getMissedExams("olematonqrcode");
        assertEquals(0, missDates.size());
    }
    
    @Test
    public void testGet1MissedExams() throws Exception {
        List<Date> missDates = missedExamDao.getMissedExams("0kkopa143562150");
        assertEquals(0, missDates.size());
        missedExamDao.addMissedExam("0kkopa143562150");
        missDates = missedExamDao.getMissedExams("0kkopa143562150");
        assertEquals(1, missDates.size());
    }
    
    @Test
    public void testGet5MissedExams() throws Exception {
        List<Date> missDates = missedExamDao.getMissedExams("0kkopa143562150");
        assertEquals(0, missDates.size());
        for (int i = 0; i < 5; i++) {
            missedExamDao.addMissedExam("0kkopa143562150");
        }
        missDates = missedExamDao.getMissedExams("0kkopa143562150");
        assertEquals(5, missDates.size());
    }
}