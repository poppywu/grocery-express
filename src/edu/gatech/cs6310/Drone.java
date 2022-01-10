package edu.gatech.cs6310;

import java.util.HashSet;

public class Drone {
    private final Integer id;
    private final Integer totalCapacity;
    private Integer tripsRemaining;
    private Integer remainingCapacity;
    private HashSet<String> orders;
    private Pilot currentPilot;


    public Drone(Integer id, Integer cap, Integer service) {
        this.id = id;
        this.totalCapacity = cap;
        this.remainingCapacity = cap;
        this.tripsRemaining = service;
        this.orders = new HashSet<>();
    }

    public Integer getTripsRemaining() { return tripsRemaining; };

    public void completeOrder() {
        tripsRemaining--;
    }

    public void addOrder(String orderId) { orders.add(orderId); }

    public void removeOrder(String orderId) { orders.remove(orderId); }

    public Integer getId() { return id; }

    public Integer getRemainingCapacity() { return remainingCapacity; }

    public void setRemainingCapacity(Integer remainingCapacity) { this.remainingCapacity = remainingCapacity; }

    public void setCurrentPilot(Pilot pilot) { currentPilot=pilot; }

    public Pilot getCurrentPilot() { return currentPilot; }

    public void displayDrone() {
        if(currentPilot == null) {
            System.out.println("droneID:" + id + ",total_cap:" + totalCapacity + ",num_orders:" + orders.size() + ",remaining_cap:" + remainingCapacity + ",trips_left:" + tripsRemaining);
            return;
        }
        System.out.println("droneID:" + id + ",total_cap:" + totalCapacity + ",num_orders:" + orders.size() + ",remaining_cap:" + remainingCapacity + ",trips_left:" + tripsRemaining + ",flown_by:" + currentPilot.fullName());
    }
}
