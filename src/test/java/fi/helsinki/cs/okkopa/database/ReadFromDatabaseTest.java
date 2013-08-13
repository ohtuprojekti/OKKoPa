package fi.helsinki.cs.okkopa.database;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReadFromDatabaseTest {

    QRCodeDatabase database;

    public ReadFromDatabaseTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, SQLException, IOException {
        database = new QRCodeDatabase(new Settings("databasetestsettings.xml"));
        
        database.addQRCode("0kkopa142921400");
        database.addUSer("0kkopa142921400","ktunnus");
        
        database.addQRCode("0kkopa143562150");
    }

    @After
    public void tearDown() throws SQLException {
        database.closeConnectionSource();
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingUserIdFromDatabase() throws NotFoundException, SQLException {
        database.getUserID("eripogjh");
    }
    
    @Test
    public void getUserIdFromDatabase() throws NotFoundException, SQLException {
        String userId = database.getUserID("0kkopa142921400");
        assertEquals("ktunnus", userId);
    }
    
    @Test
    public void addQRCodeToDatabase() throws SQLException, NotFoundException {
        database.addQRCode("0kkopa142245400");
        assertEquals("", database.getUserID("0kkopa142245400"));
        
        assertEquals(false, database.addQRCode("0kkopa142245400"));
        
        database.addUSer("0kkopa142245400", "testi2u");
        assertEquals("testi2u", database.getUserID("0kkopa142245400"));
    }
}