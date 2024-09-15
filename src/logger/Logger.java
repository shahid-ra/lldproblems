package logger;

import logger.logappender.ConsoleAppender;

import java.util.Date;

public class Logger {
    private static Logger _instance;
    private LoggerConfig config;
    private Logger() {
        this.config = new LoggerConfig(LogLevel.INFO, new ConsoleAppender());
    }

    public static synchronized Logger getInstance() {
        if (_instance == null) {
            _instance = new Logger();
        }
        return _instance;
    }

    public LoggerConfig getConfig() {
        return config;
    }

    public void setConfig(LoggerConfig config) {
        this.config = config;
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() >= config.getLevel().ordinal()) {
            LogMessage logMessage = new LogMessage(level, message);
            this.config.getLogAppender().append(logMessage);
        }
    }

    public void debug(String message) {
        this.log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        this.log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        this.log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        this.log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        this.log(LogLevel.FATAL, message);
    }
}
