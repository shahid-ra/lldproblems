package logger;

import java.util.Date;

public class LogMessage {
    private final LogLevel level;
    private final String content;
    private final Date timestamp;

    public LogMessage(LogLevel level, String content) {
        this.level = level;
        this.content = content;
        this.timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public LogLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "[level=" + level + ", content=" + content + ", timestamp=" + timestamp + "]";
    }
}
