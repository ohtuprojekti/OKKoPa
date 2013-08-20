package fi.helsinki.cs.okkopa.main.stage;

import fi.helsinki.cs.okkopa.mail.read.EmailRead;
import fi.helsinki.cs.okkopa.main.ExceptionLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetEmailStage extends Stage<Object, InputStream> {

    private static final Logger LOGGER = Logger.getLogger(GetEmailStage.class.getName());
    private EmailRead imapServer;
    private ExceptionLogger exceptionLogger;

    @Autowired
    public GetEmailStage(EmailRead server, ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
        this.imapServer = server;
    }

    @Override
    public void process(Object in) {
        try {
            imapServer.connect();
            LOGGER.debug("Kirjauduttu sisään.");
            while (true) {
                List<InputStream> attachments = imapServer.getNextMessagesAttachments();
                LOGGER.debug("Vanhimman viestin liitteet haettu");
                if (attachments == null) {
                    LOGGER.debug("Ei uusia viestejä.");
                    break;
                }
                for (InputStream attachment : attachments) {
                    LOGGER.debug("Käsitellään liitettä.");
                    processNextStages(attachment);
                    IOUtils.closeQuietly(attachment);
                }
            }
        } catch (MessagingException | IOException ex) {
            exceptionLogger.logException(ex);
        } finally {
            imapServer.close();
        }
    }
}
