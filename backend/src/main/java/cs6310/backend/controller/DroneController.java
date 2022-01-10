package cs6310.backend.controller;

import cs6310.backend.model.Drone;
import cs6310.backend.model.DroneId;
import cs6310.backend.model.Pilot;
import cs6310.backend.model.Store;
import cs6310.backend.service.DroneService;
import cs6310.backend.service.HelperService;
import cs6310.backend.service.LoggerService;
import cs6310.backend.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DroneController {
    @Autowired
    private DroneService droneService;
    @Autowired
    private HelperService helperService;
    @Autowired
    private PilotService pilotService;
    @Autowired
    private LoggerService loggerService;

    @PostMapping(path = "/private/make_drone")
    public ResponseEntity<String> addDrone(@RequestBody Map<String, String> ip) {
        if (!helperService.checkStore(ip.get("storeName")))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        if (!helperService.checkDrone(new DroneId(Integer.parseInt(ip.get("id")), ip.get("storeName")))) {
            Drone drone = new Drone(new DroneId(Integer.parseInt(ip.get("id")), ip.get("storeName")), Integer.parseInt(ip.get("cap")), Integer.parseInt(ip.get("service")));
            drone.setStore(helperService.getStore(ip.get("storeName")));
            droneService.save(drone);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:drone_identifier_already_exists");
        }
        loggerService.writeLog("make_drone", ip);
        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }

    @GetMapping(path = "/private/display_drones/{store_name}")
    public ResponseEntity<String> getDrones(@PathVariable("store_name") String storeName) {
        if (!helperService.checkStore(storeName))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("store_name", storeName);
        loggerService.writeLog("display_drones", m);
        return ResponseEntity.status(HttpStatus.OK).body(droneService.getDrones(storeName));
    }

    @PostMapping(path = "/private/fly_drone")
    public ResponseEntity<String> flyDrone(@RequestBody Map<String, String> ip) {
        if (!helperService.checkStore(ip.get("storeName")))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        if (!helperService.checkDrone(new DroneId(Integer.parseInt(ip.get("droneId")), ip.get("storeName"))))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:drone_identifier_does_not_exist");
        if (!pilotService.checkPilot(ip.get("pilotId")))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:pilot_identifier_does_not_exist");

        Store s = helperService.getStore(ip.get("storeName"));
        Drone d = droneService.getDrone(s, Integer.parseInt(ip.get("droneId")));
        Pilot p = pilotService.getPilot(ip.get("pilotId"));
        Drone oldDrone = droneService.getDroneByPilot(ip.get("pilotId"));
        if(oldDrone!=null){
            oldDrone.setPilot(null);
            droneService.update(oldDrone);
        }
        d.setPilot(p);
        droneService.update(d);
        loggerService.writeLog("fly_drone", ip);

        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }
}
