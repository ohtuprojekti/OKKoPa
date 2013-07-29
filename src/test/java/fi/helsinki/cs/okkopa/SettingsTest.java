/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;

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
public class SettingsTest {
    
    private Settings testSettings;
    
    public SettingsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {     
        //before each test
        testSettings = new Settings("src/test/resources/test.xml");
    }
    
    @After
    public void tearDown() {
        //after all the tests
    }

    @Test
    public void testSettingNotNull() {
        assertNotNull("Loading \"test.xml\" caused an exception. Check for correct path or whether the file exists.",testSettings.getSettings());
    }
  
    
    @Test 
    public void testSettingContainsValues() {
       assertTrue("test.xml has no content, should contain at least 1 key value pair.",testSettings.getSettings().size() == 11);
    }


}
