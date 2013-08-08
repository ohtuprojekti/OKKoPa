/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.ldap;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.KeyStoreKeyManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.Student;
import java.security.GeneralSecurityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapConnector {

    private Settings settings;
    private static Logger LOGGER = Logger.getLogger(LdapConnector.class.getName());
    private String searchFilter;
    private String baseOU;

    @Autowired
    public LdapConnector(Settings settings) {
        this.settings = settings;
        this.searchFilter = "(uid=%s)";
        this.baseOU = "dc=helsinki,dc=fi";
    }

    public Student fetchStudent(String username) throws LDAPException, GeneralSecurityException, NotFoundException {
        SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager(settings.getSettings().getProperty("ldap.keystore.file"), settings.getSettings().getProperty("ldap.keystore.secret").toCharArray()), new TrustAllTrustManager(true));
        LDAPConnection ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), settings.getSettings().getProperty("ldap.server.address"), Integer.parseInt(settings.getSettings().getProperty("ldap.server.port")));
        SearchResult result = ldc.search(baseOU, SearchScope.SUBORDINATE_SUBTREE, String.format(searchFilter, username), "mail", "schacPersonalUniqueCode");

        if (result.getEntryCount() > 1) {
            throw new NotFoundException("Too many results from LDAP-query with username " + username + ".");
        }

        if (result.getEntryCount() < 1) {
            throw new NotFoundException("No student information returned from LDAP.");
        }
        
        Student currentStudent = new Student();
        SearchResultEntry entry = result.getSearchEntries().get(0);
        currentStudent.setEmail(entry.getAttributeValue("mail"));
        currentStudent.setStudentNumber(entry.getAttributeValue("schacPersonalUniqueCode"));
        currentStudent.setUsername(username);
        return currentStudent;
    }
    
    

//    public static void main(String[] args) {
//        test();
//    }
//
//    public static void test() {
//        try {
//            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager("src/main/resources/keystore", "okkopa2013".toCharArray()), new TrustAllTrustManager(true));
//            LDAPConnection ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), "ldap-internal.it.helsinki.fi", 636);
//            SearchResult result = ldc.search("ou=org,o=hy", SearchScope.SUBORDINATE_SUBTREE, "(ou=A02700)", null);
//            System.out.println("Found: " + result.getEntryCount() + " results.");
//
//            List<SearchResultEntry> entry = result.getSearchEntries();
//            System.out.println(entry.get(0).getAttributeValue("postalAddress"));
//
//
//            ldc.close();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//
//
//    }
}