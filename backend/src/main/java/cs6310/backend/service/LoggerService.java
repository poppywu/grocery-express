package cs6310.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class LoggerService {
    Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public void writeLog(String method, Map<String, String> body) {
        File logDir = new File("./logs/");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
        String fileName = "./logs/application-log.txt";
        File logFile = new File(fileName);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        try {
            logFile.createNewFile();
            String logString = timeStamp + " " + method;
            for (Map.Entry<String, String> entry: body.entrySet()) {
                logString += ", " + entry.getKey() + ": " + entry.getValue();
            }
            logString += " ; ";
            Files.writeString(Paths.get(fileName), logString, StandardOpenOption.APPEND);
        } catch (IOException error) {
            logger.error(error.toString());
        }
    }
}
