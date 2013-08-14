package fi.helsinki.cs.okkopa.main.stage;

import com.unboundid.ldap.sdk.LDAPException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.ldap.LdapConnector;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.main.Settings;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.security.GeneralSecurityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveToTikliStage extends Stage<ExamPaper, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(SaveToTikliStage.class.getName());
    private boolean saveToTikli;
    private LdapConnector ldapConnector;
    private ExceptionLogger exceptionLogger;

    @Autowired
    public SaveToTikliStage(LdapConnector ldapConnector, Settings settings,
            ExceptionLogger exceptionLogger) {
        this.ldapConnector = ldapConnector;
        saveToTikli = Boolean.parseBoolean(settings.getProperty("tikli.enable"));
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(ExamPaper examPaper) {
        if (examPaper.getCourseInfo() != null && saveToTikli) {
            // Get student number from LDAP:
            try {
                ldapConnector.setStudentInfo(examPaper.getStudent());
                saveToTikli(examPaper);
                LOGGER.debug("Koepaperi tallennettu Tikliin.");
            } catch (NotFoundException | LDAPException | GeneralSecurityException ex) {
                exceptionLogger.logException(ex);
            }
        }
    }

    private void saveToTikli(ExamPaper examPaper) {
        LOGGER.info("Tässä vaiheessa tallennettaisiin paperit Tikliin");
    }
}
