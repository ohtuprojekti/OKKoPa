/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.ldap;

import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.SimpleBindRequest;
import com.unboundid.util.Debug;
import com.unboundid.util.ssl.KeyStoreKeyManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.net.SocketFactory;
import fi.helsinki.cs.okkopa.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.Student;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapConnector {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Debug.debugEnabled();
        try {
            LDAPConnectionOptions options = new LDAPConnectionOptions();
            //      options.setAutoReconnect(true);
            options.setConnectTimeoutMillis(30000);
            LDAPConnection ldc = new LDAPConnection(options);
            ldc.connect("ldap-internal.it.helsinki.fi", 636);
//
//
            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager("src/main/resources/hyad_root", "okkopa2013".toCharArray()), new TrustAllTrustManager(true));
            SearchResult result = ldc.search("ou=org,o=hy", SearchScope.SUBORDINATE_SUBTREE, "(ou=A02700)", "postalAddress");
            System.out.println("Found: " + result.getEntryCount() + " results.");
            ldc.close();
        } catch (Exception ex) {
            Logger.getLogger(LdapConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Settings settings;
    private static Logger LOGGER = Logger.getLogger(LdapConnector.class.getName());
    private String searchFilter;
    private String baseOU;
    private String bindDN;
    private String bindPWD;

    @Autowired
    public LdapConnector(Settings settings) {
        this.settings = settings;
        this.searchFilter = "(uid=%s)";
        this.baseOU = "dc=helsinki,dc=fi";
        this.bindDN = "uid=" + settings.getSettings().getProperty("ldap.user") + baseOU;
        this.bindPWD = settings.getSettings().getProperty("ldap.password");
    }

    public Student fetchStudent(String username) throws NotFoundException, GeneralSecurityException, LDAPException {
        LDAPConnection ldc = null;

        try {
            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager(settings.getSettings().getProperty("ldap.keystore.file"), settings.getSettings().getProperty("ldap.keystore.secret").toCharArray()), new TrustAllTrustManager(true));
            ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), settings.getSettings().getProperty("ldap.server.address"), Integer.parseInt(settings.getSettings().getProperty("ldap.server.port")));
    //Authentication:
    //        SimpleBindRequest bindReq = new SimpleBindRequest(bindDN,bindPWD);
    //        bindReq.setResponseTimeoutMillis(1000);
    //        ldc.bind(bindReq);

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

        } catch (LDAPException | GeneralSecurityException | NotFoundException ex) {
            if (ldc != null) {
                ldc.close();
            }
            throw ex;
        }
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