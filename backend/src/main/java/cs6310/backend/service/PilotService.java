package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.PilotRepository;
import cs6310.backend.model.Pilot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class PilotService {
    Logger logger = LoggerFactory.getLogger(PilotService.class);
    @Autowired
    private PilotRepository pilotRepository;
    @Autowired
    private HelperService helperService;

    public void save(Pilot pilot) {
        TextEncryptor encryptor = Encryptors.text("taxId", "5c0744940b5c369c");
        pilot.setTaxId(encryptor.encrypt(pilot.getTaxId()));
        try {
            pilotRepository.findById(pilot.getUsername()).get();
            logger.error("Pilot Service : Save Pilot Failed Due To Pilot Already Exist At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Pilot Already Exists");
        } catch (NoSuchElementException error) {
            logger.info("Pilot Service : Save Pilot Success At " + helperService.getTimeStamp());
            pilotRepository.save(pilot);
        }
    }

    public Boolean checkLicense(Pilot pilot) {
        try {
            if (pilotRepository.findPilotByLicenseId(pilot.getLicenseId()).size() > 0) {
                Pilot p = pilotRepository.findPilotByLicenseId(pilot.getLicenseId()).get(0);
                if (p.getLicenseId().equals(pilot.getLicenseId())) {
                    return true;
                }
            }

        } catch (NoSuchElementException ignored) {
        }
        return false;
    }

    public Boolean checkPilot(String username) {
        return pilotRepository.findById(username).isPresent();
    }

    public Pilot getPilot(String pilot) {
        return pilotRepository.findPilotByUsername(pilot);
    }

    public String getPilots() {
        ArrayList<String> resp = new ArrayList<>();
        TextEncryptor encryptor = Encryptors.text("taxId", "5c0744940b5c369c");
        pilotRepository.findAll().forEach(pilot -> {
            pilot.setTaxId(encryptor.decrypt(pilot.getTaxId()));
            resp.add(pilot.displayPilot());
        });
        resp.add("OK:display_completed");
        logger.info("Pilot Service : Get Pilots Success At " + helperService.getTimeStamp());
        return String.join("\n", resp);
    }
}
