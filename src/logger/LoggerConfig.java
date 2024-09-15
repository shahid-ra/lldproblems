package logger;

import logger.logappender.LogAppender;

public class LoggerConfig {
    private final LogLevel level;
    private final LogAppender logAppender;

    public LoggerConfig(LogLevel level, LogAppender logAppender) {
        this.level = level;
        this.logAppender = logAppender;
    }

    public LogAppender getLogAppender() {
        return logAppender;
    }

    public LogLevel getLevel() {
        return level;
    }
}
