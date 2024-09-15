package logger.logappender;

import logger.LogMessage;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender{
    private final String filePath;

    public FileAppender(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(LogMessage message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
