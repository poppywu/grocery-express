package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Drone")
public class Drone {
    @EmbeddedId
    private DroneId droneId;
    private Integer totalCapacity;
    private Integer tripsRemaining;
    private Integer remainingCapacity;

    @JsonManagedReference
    @OneToMany(mappedBy = "drone",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final Set<Order> orders = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pilot_id", referencedColumnName = "username")
    private Pilot pilot;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeName")
    @JoinColumn(name = "store_name", referencedColumnName = "name")
    private Store store;

    public Drone(DroneId droneId, Integer cap, Integer service) {
        this.droneId = droneId;
        this.totalCapacity = cap;
        this.remainingCapacity = cap;
        this.tripsRemaining = service;
    }

    public Drone() {}

    public Integer getTripsRemaining() {
        return tripsRemaining;
    }

    public void completeOrder() { tripsRemaining--; }

    public void addOrder(Order order) { orders.add(order); }

    public void removeOrder(Order order) { orders.remove(order); }

    public DroneId getDroneId() {
        return droneId;
    }

    public Integer getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(Integer remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public Pilot getPilot() { return pilot; }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String displayDrone() {
        if (pilot == null) {
            return "droneID:" + droneId.getId() + ",total_cap:" + totalCapacity + ",num_orders:" + orders.size() + ",remaining_cap:" + remainingCapacity + ",trips_left:" + tripsRemaining;
        }
        return "droneID:" + droneId.getId() + ",total_cap:" + totalCapacity + ",num_orders:" + orders.size() + ",remaining_cap:" + remainingCapacity + ",trips_left:" + tripsRemaining + ",flown_by:" + pilot.fullName();
    }
}
