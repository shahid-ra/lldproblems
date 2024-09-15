package logger;

import logger.logappender.ConsoleAppender;
import logger.logappender.FileAppender;

public class MainClass {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.setConfig(new LoggerConfig(
                LogLevel.WARNING,
                new FileAppender("") // path to your log file
        ));
        logger.debug("This is a debug message");
        logger.info("This is a info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }
}
