package fi.helsinki.cs.okkopa.ldap;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.SimpleBindRequest;
import com.unboundid.util.ssl.KeyStoreKeyManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import java.util.logging.Logger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.Student;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapConnector {

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
        this.bindDN = settings.getSettings().getProperty("ldap.user");
        this.bindPWD = settings.getSettings().getProperty("ldap.password");
    }

    public Student fetchStudent(String username) throws NotFoundException, GeneralSecurityException, LDAPException {
        LDAPConnection ldc = null;

        try {
            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager(settings.getSettings().getProperty("ldap.keystore.file"), settings.getSettings().getProperty("ldap.keystore.secret").toCharArray()), new TrustAllTrustManager(true));
            ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), settings.getSettings().getProperty("ldap.server.address"), Integer.parseInt(settings.getSettings().getProperty("ldap.server.port")));

            SimpleBindRequest bindReq = new SimpleBindRequest(bindDN,bindPWD);
            bindReq.setResponseTimeoutMillis(1000);
            ldc.bind(bindReq);

            SearchResult result = ldc.search(baseOU, SearchScope.SUBORDINATE_SUBTREE, String.format(searchFilter, username), "mail", "schacPersonalUniqueCode");

            if (result.getEntryCount() > 1) {
                throw new NotFoundException("Too many results from LDAP-query with username " + username + ".");
            }

            if (result.getEntryCount() < 1) {
                throw new NotFoundException("No student information returned from LDAP.");
            }

            Student currentStudent = new Student();
            SearchResultEntry entry = result.getSearchEntries().get(0);
//          currentStudent.setEmail(entry.getAttributeValue("mail"));
            currentStudent.setEmail("okkopa.2013@gmail.com");
            String[] strArr = entry.getAttributeValue("schacPersonalUniqueCode").split(":");
            String studentNumber = strArr[strArr.length-1];
            currentStudent.setStudentNumber(studentNumber);
            currentStudent.setUsername(username);
            LOGGER.info("Found student with student number: "+ studentNumber);


            return currentStudent;

        } catch (LDAPException | GeneralSecurityException | NotFoundException ex) {
            if (ldc != null) {
                ldc.close();
            }
            throw ex;
        }
    }
}