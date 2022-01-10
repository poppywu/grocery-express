package cs6310.backend.dao;

import cs6310.backend.model.Drone;
import cs6310.backend.model.DroneId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DroneRepository extends JpaRepository<Drone, DroneId> {
    Drone getDroneByDroneId(DroneId droneId);
    @Modifying
    @Query("update Drone d set d.remainingCapacity=:remainCap where d.droneId=:id")
    public void updateRemainCap(@Param("remainCap") Integer remainCap, @Param("id")DroneId droneId);
    Drone findDroneByDroneId(DroneId droneId);
    Drone findDroneByPilotUsername(String username);
}
