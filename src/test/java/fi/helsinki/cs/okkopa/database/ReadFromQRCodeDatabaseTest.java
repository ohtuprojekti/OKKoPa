package fi.helsinki.cs.okkopa.database;

import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReadFromQRCodeDatabaseTest {

    OkkopaDatabaseConnectionSource connectionSource;
    QRCodeDAO database;

    public ReadFromQRCodeDatabaseTest() {
    }

    @Before
    public void setUp() throws FileNotFoundException, SQLException, IOException {
         connectionSource = new OkkopaDatabaseConnectionSource(new Settings("databasetestsettings.xml"));
        database = new QRCodeDAO(connectionSource);

        database.addQRCode("0kkopa142921400");
        database.addUser("0kkopa142921400", "ktunnus");

        database.addQRCode("0kkopa143562150");
    }

    @After
    public void tearDown() {
        connectionSource.closeQuietly();
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
        assertNull(database.getUserID("0kkopa142245400"));

        assertEquals(false, database.addQRCode("0kkopa142245400"));

        database.addUser("0kkopa142245400", "testi2u");
        assertEquals("testi2u", database.getUserID("0kkopa142245400"));
    }
}