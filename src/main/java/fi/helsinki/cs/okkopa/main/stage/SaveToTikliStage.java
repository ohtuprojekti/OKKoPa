package fi.helsinki.cs.okkopa.main.stage;

import com.unboundid.ldap.sdk.LDAPException;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import java.security.GeneralSecurityException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SaveToTikliStage extends Stage<ExamPaper, ExamPaper> {

    private static Logger LOGGER = Logger.getLogger(SaveToTikliStage.class.getName());

    @Override
    public void process(ExamPaper examPaper) {
//// Get email and student number from LDAP:
//        try {
//            examPaper.setStudent(ldapConnector.fetchStudent(currentUserId));
//        } catch (NotFoundException | LDAPException | GeneralSecurityException ex) {
//            logException(ex);
//        }
//
//        // TODO remove when ldap has been implemented.
//        //examPaper.setStudent(new Student(currentUserId, "okkopa.2013@gmail.com", "dummystudentnumber"));
//
//        sendEmail(examPaper, true);
//        LOGGER.debug("Koepaperi lähetetty sähköpostilla.");
//        if (courseInfo != null && saveToTikli) {
            saveToTikli(examPaper);
//            LOGGER.debug("Koepaperi tallennettu Tikliin.");
//        }
    }

    private void saveToTikli(ExamPaper examPaper) {
        LOGGER.info("Tässä vaiheessa tallennettaisiin paperit Tikliin");
    }
}
