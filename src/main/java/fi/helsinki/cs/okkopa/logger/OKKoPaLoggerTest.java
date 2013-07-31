package fi.helsinki.cs.okkopa.logger;

import org.apache.log4j.Logger;

public class OKKoPaLoggerTest {

    private static final Logger LOGGER = Logger.getLogger(OKKoPaLoggerTest.class.getName());

    public static void main(String[] args) {

        LOGGER.debug("Sample debug message");

        LOGGER.info("Sample info message");

        LOGGER.warn("Sample warn message");

        LOGGER.error("Sample error message");

        LOGGER.fatal("Sample fatal message");

    }
}
