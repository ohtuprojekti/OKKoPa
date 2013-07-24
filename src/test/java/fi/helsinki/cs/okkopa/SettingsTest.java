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
import java.util.Properties;
import static org.junit.Assert.*;

/**
 *
 * @author phemmila
 */
public class SettingsTest {
    
    public static Properties imapTestProps, smtpTestProps, pwdTestProps;
    
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
        assertNotNull(Settings.IMAPPROPS);
    }
    
    @Test
    public void smtpPropsNotNull() {
        assertNotNull(Settings.SMTPPROPS);
    }
    
    @Test
    public void pwdPropsNotNull() {
        assertNotNull(Settings.PWDPROPS);
    }
    
    @Test 
    public void testImapPropsContainsValues() {
       assertTrue(Settings.IMAPPROPS.size() > 0);
    }
    
    @Test 
    public void testSmtpPropsContainsValues() {
       assertTrue(Settings.SMTPPROPS.size() > 0);
    }
    
    @Test
    public void testPwdPropsContainsValues() {
        assertTrue(Settings.PWDPROPS.size() > 0);
    }

}