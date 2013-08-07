/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.ldap;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.KeyStoreKeyManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;

/**
 *
 * @author phemmila
 */
public class LdapConnector {
    
    public static void main(String[] args) {
        test();
    }
    
    public static void test() {
        try {
            
            
            
            SSLUtil sslUtil = new SSLUtil(new KeyStoreKeyManager("src/main/resources/hyad_root", "okkopa2013".toCharArray()),new TrustAllTrustManager(true));           
            LDAPConnection ldc = new LDAPConnection(sslUtil.createSSLSocketFactory(), "ldap-internal.it.helsinki.fi", 636);
            SearchResult result = ldc.search("ou=org,o=hy", SearchScope.SUBORDINATE_SUBTREE, "(ou=A02700)", "postalAddress");
            System.out.println("Found: "+result.getEntryCount()+" results.");
            ldc.close();
        } catch (Exception ex) {
            Logger.getLogger(LdapConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}