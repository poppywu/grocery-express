package cs6310.backend.dao;

import cs6310.backend.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PilotRepository extends JpaRepository<Pilot, String> {
    List<Pilot> findPilotByLicenseId(String licenseId);
    Pilot findPilotByUsername(String username);
}
