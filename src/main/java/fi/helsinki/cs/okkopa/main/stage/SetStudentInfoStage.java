package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.database.QRCodeDAO;
import fi.helsinki.cs.okkopa.exception.NotFoundException;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import fi.helsinki.cs.okkopa.model.ExamPaper;
import fi.helsinki.cs.okkopa.model.Student;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetStudentInfoStage extends Stage<ExamPaper, ExamPaper> {

    private static final Logger LOGGER = Logger.getLogger(SetStudentInfoStage.class.getName());
    private ExceptionLogger exceptionLogger;
    private QRCodeDAO qRCodeDatabase;

    @Autowired
    public SetStudentInfoStage(QRCodeDAO qRCodeDatabase, ExceptionLogger exceptionLogger) {
        this.qRCodeDatabase = qRCodeDatabase;
        this.exceptionLogger = exceptionLogger;
    }

    @Override
    public void process(ExamPaper examPaper) {
        try {
            LOGGER.debug("ID: " + examPaper.getQRCodeString());
            String userId = fetchUserId(examPaper.getQRCodeString());
            Student student = new Student();
            examPaper.setStudent(student);
            student.setUsername(userId);
            // TODO katenointi
            student.setEmail("okkopa.2013@gmail.com");
        } catch (SQLException | NotFoundException ex) {
            exceptionLogger.logException(ex);
            LOGGER.debug("Luettu QR-koodi ei ollut käyttäjätunnus eikä sitä vastannut yksikään geneerinen tunnus.");
            // QR code isn't an user id and doesn't match any database entries.
            return;
        }
        processNextStages(examPaper);
    }

    private String fetchUserId(String qrcode) throws SQLException, NotFoundException {
        // Filter too short
        if (qrcode.length() == 0) {
            throw new NotFoundException();
        }
        // Check database
        if (Character.isDigit(qrcode.charAt(0))) {
            return qRCodeDatabase.getUserID(qrcode);
        }
        // Filter if has digits
        for (char c : qrcode.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new NotFoundException();
            }
        }
        return qrcode;
    }
}
