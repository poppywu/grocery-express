package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.DroneRepository;
import cs6310.backend.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DroneService {
    Logger logger = LoggerFactory.getLogger(DroneService.class);
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private HelperService helperService;

    public void save(Drone drone) {
        if (helperService.checkDrone(drone.getDroneId())) {
            logger.error("Drone Service : Drone Save Failed Due To Drone Already Exists At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Drone Already Exists");
        } else {
            droneRepository.save(drone);
            logger.info("Drone Service : Drone Save Success At " + helperService.getTimeStamp());
        }
    }

    public void update(Drone drone) {
        droneRepository.save(drone);
        logger.info("Drone Service : Drone Update Success At " + helperService.getTimeStamp());
    }

    public Drone getDrone(Store store, Integer droneId) {
        return droneRepository.getDroneByDroneId(new DroneId(droneId, store.getName()));
    }

    public Drone getDroneByPilot(String pilotId) {
        return droneRepository.findDroneByPilotUsername(pilotId);
    }

    public String getDrones(String storeName) {
        ArrayList<String> resp = new ArrayList<>();
        droneRepository.findAll().forEach(drone -> {
            if (drone.getStore().getName().equals(storeName))
                resp.add(drone.displayDrone());
        });
        resp.add("OK:display_completed");
        logger.info("Drone Service : Get Drones of Store " + storeName + " Success At" + helperService.getTimeStamp());
        return String.join("\n", resp);
    }

    public Boolean canCarryNewLine(Line line, Order order) {
        return order.getDrone().getRemainingCapacity() >= line.getTotalWeight();
    }

    public void updateRemainCap(DroneId droneId, Integer newRemainCap) {
        droneRepository.updateRemainCap(newRemainCap, droneId);
    }
}
