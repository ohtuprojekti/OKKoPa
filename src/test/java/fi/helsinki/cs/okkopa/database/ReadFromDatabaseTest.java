/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.database;

import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phemmila
 */
public class ReadFromDatabaseTest {

    OkkopaDatabase database;

    public ReadFromDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException, SQLException, IOException {
        database = new OkkopaDatabase(new Settings("databasetestsettings.xml"));
        database.addQRCode("0kkopa142921400", "ktunnus");
    }

    @After
    public void tearDown() {
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingUserIdFromDatabase() throws NotFoundException, SQLException {
        database.getUserID("");
    }
    
    @Test
    public void getUserIdFromDatabase() throws NotFoundException, SQLException {
        String userId = database.getUserID("0kkopa142921400");
        assertEquals("ktunnus", userId);
    }
    
    @Test
    public void addQRCodeToDatabase() {
        
    }
}