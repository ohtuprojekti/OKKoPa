package fi.helsinki.cs.okkopa.main;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionLogger {

    private static Logger LOGGER = Logger.getLogger(ExceptionLogger.class.getName());
    private final boolean logCompleteExceptionStack;

    @Autowired
    public ExceptionLogger(Settings settings) {
        logCompleteExceptionStack = Boolean.parseBoolean(settings.getProperty("logger.logcompletestack"));
    }

    public void logException(Exception ex) {
        //Currently just logging exceptions.
        if (logCompleteExceptionStack) {
            LOGGER.error(ex.toString(), ex);
        } else {
            LOGGER.error(ex.toString());
        }
    }
}
