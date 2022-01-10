package edu.gatech.cs6310;

import java.util.Map;
import java.util.TreeMap;

public class Store {
    private final String name;
    private Integer revenue;
    private final Map<String, Item> items;
    private final Map<Integer, Drone> drones;
    private final Map<String, Order> orders;

    public Store(String name, Integer revenue) {
        this.name = name;
        this.revenue = revenue;
        this.items = new TreeMap<>();
        this.drones = new TreeMap<>();
        this.orders = new TreeMap<>();
    }

    public void displayStore() {
        System.out.println("name:" + name + ",revenue:" + revenue);
    }

    public void addItem(Item item){
        this.items.put(item.getName(), item);
    }

    public Map<String, Item> getItems() { return this.items; }

    public void addDrone(Drone drone){ this.drones.put(drone.getId(), drone); }

    public Map<Integer, Drone> getDrones() {
        return this.drones;
    }

    public void addOrder(Order order){ this.orders.put(order.getId(), order); }

    public Map<String, Order> getOrders() { return this.orders; }

    public Integer getRevenue() { return revenue; }

    public void setRevenue(Integer revenue) { this.revenue = revenue; }
}
