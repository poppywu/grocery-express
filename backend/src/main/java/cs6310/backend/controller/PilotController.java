package cs6310.backend.controller;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.model.Pilot;
import cs6310.backend.service.LoggerService;
import cs6310.backend.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PilotController {
    @Autowired
    private PilotService pilotService;

    @Autowired
    private LoggerService loggerService;

    @PostMapping(path="/private/make_pilot")
    public ResponseEntity<String> addPilot(@RequestBody Map<String, String> cp){
        try {
            Pilot pilot = new Pilot(
                    cp.get("username"), cp.get("firstName"), cp.get("lastName"),
                    cp.get("phoneNum"), cp.get("taxId"), cp.get("licenseId"), Integer.parseInt(cp.get("experience"))
            );
            if(pilotService.checkLicense(pilot)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:pilot_license_already_exists");
            }
            pilotService.save(pilot);
            loggerService.writeLog("make_pilot", cp);
            return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
        } catch (DuplicateRequestException error){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:pilot_identifier_already_exists");
        }
    }
    @GetMapping(path="/private/display_pilots")
    public ResponseEntity<String> getPilots() {
        loggerService.writeLog("display_pilots", new HashMap<String, String>());
        return ResponseEntity.status(HttpStatus.OK).body(pilotService.getPilots());
    }
}
