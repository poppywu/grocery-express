package cs6310.backend.controller;

import cs6310.backend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class LogController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping(path = "/private/logs")
    public String getLogs() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./logs/application-log.txt"), StandardCharsets.US_ASCII);
        return lines.toString();
    }
}

