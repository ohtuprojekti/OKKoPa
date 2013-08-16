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
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.Student;
import java.security.GeneralSecurityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LdapConnector {

    private Settings settings;
    private static final Logger LOGGER = Logger.getLogger(LdapConnector.class.getName());
    private String searchFilter;
    private String baseOU;
    private String bindDN;
    private String bindPWD;

    @Autowired
    public LdapConnector(Settings settings) {
        this.settings = settings;
        this.searchFilter = "(uid=%s)";
        this.baseOU = "dc=helsinki,dc=fi";
        this.bindDN = settings.getProperty("ldap.user");
        this.bindPWD = settings.getProperty("ldap.password");
    }

    public Student setStudentInfo(Student student) throws NotFoundException, GeneralSecurityException, LDAPException {
        LDAPConnection ldc = null;

        try {
            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager(settings.getProperty("ldap.keystore.file"), settings.getProperty("ldap.keystore.secret").toCharArray()), new TrustAllTrustManager(true));
            ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), settings.getProperty("ldap.server.address"), Integer.parseInt(settings.getProperty("ldap.server.port")));

            SimpleBindRequest bindReq = new SimpleBindRequest(bindDN, bindPWD);
            bindReq.setResponseTimeoutMillis(1000);
            ldc.bind(bindReq);

            SearchResult result = ldc.search(baseOU, SearchScope.SUBORDINATE_SUBTREE, String.format(searchFilter, student.getUsername()), "mail", "schacPersonalUniqueCode");

            if (result.getEntryCount() > 1) {
                throw new NotFoundException("Too many results from LDAP-query with username " + student.getUsername() + ".");
            }

            if (result.getEntryCount() < 1) {
                throw new NotFoundException("No student information returned from LDAP.");
            }

            SearchResultEntry entry = result.getSearchEntries().get(0);
//          currentStudent.setEmail(entry.getAttributeValue("mail"));
            String[] strArr = entry.getAttributeValue("schacPersonalUniqueCode").split(":");
            String studentNumber = strArr[strArr.length - 1];
            //student.setStudentNumber(studentNumber);
            student.setStudentNumber("012617177");
            LOGGER.info("Found student with student number: " + studentNumber);

            return student;

        } catch (LDAPException | GeneralSecurityException | NotFoundException ex) {
            if (ldc != null) {
                ldc.close();
            }
            throw ex;
        }
    }
}