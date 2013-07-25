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
    }
    
    @After
    public void tearDown() {
        //after all the tests
    }

    @Test
    public void imapPropsNotNull() {
        assertNotNull("Loading \"imapsettings.xml\" caused an exception. Check for correct path or whether the file exists.",Settings.IMAPPROPS);
    }
    
    @Test
    public void smtpPropsNotNull() {
        assertNotNull("Loading \"smtpsettings.xml\" caused an exception. Check for correct path or whether the file exists.",Settings.SMTPPROPS);
    }
    
    @Test
    public void pwdPropsNotNull() {
        assertNotNull("Loading \"pwdsettings.xml\" caused an exception. Check for correct path or whether the file exists.",Settings.PWDPROPS);
    }
    
    @Test 
    public void imapPropsContainsValues() {
       assertTrue("imapsettings.xml has no content, should contain at least 1 key value pair.",Settings.IMAPPROPS.size() > 0);
    }
    
    @Test 
    public void smtpPropsContainsValues() {
       assertTrue("smtpsettings.xml has no content, should contain at least 1 key value pair.",Settings.SMTPPROPS.size() > 0);
    }
    
    @Test
    public void pwdPropsContainsValues() {
        assertTrue("pwdsettings.xml has no content, should contain at least 1 key value pair.", Settings.PWDPROPS.size() > 0);
    }

}