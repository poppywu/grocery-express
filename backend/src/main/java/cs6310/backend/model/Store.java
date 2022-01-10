package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store")
public class Store {
    @Id
    private String name;
    private Integer revenue;

    @JsonManagedReference
    @OneToMany(mappedBy = "store",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Item> items = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "store",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Drone> drones = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "store",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public Store() {}

    public Store(String name, Integer revenue) {
        this.name = name;
        this.revenue = revenue;
    }

    public String getName() { return this.name; }

    public String displayStore() {
       return "name:" + name + ",revenue:" + revenue;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Set<Item> getItems() { return this.items; }

    public void addDrone(Drone drone) {
        this.drones.add(drone);
    }

    public Set<Drone> getDrones() {
        return this.drones;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }
}
