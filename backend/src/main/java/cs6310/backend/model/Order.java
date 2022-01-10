package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.*;

@Entity()
@Table(name = "orders")
public class Order{
    @EmbeddedId
    private OrderId orderId;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Line> lines = new HashSet<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("name")
    @JoinColumn(name = "store_name", referencedColumnName = "name")
    private Store store;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name ="drone_id" ,referencedColumnName ="drone_id" ),@JoinColumn(name ="store_name_by_drone" ,referencedColumnName ="store_name" )})
    private Drone drone;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "username")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @PrePersist
    void createdAt() {
        this.createDate = new Date();
    }

    public Order() {}

    public Order(OrderId orderId, Store store, Drone drone, Customer customer) {
        this.orderId = orderId;
        this.store = store;
        this.drone = drone;
        this.customer = customer;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public int getCost() {
        int cost = 0;
        for (Line line : this.lines)
            cost += line.getTotalPrice();
        return cost;
    }

    public int getWeight() {
        int weight = 0;
        for (Line line : this.lines)
            weight += line.getTotalWeight();
        return weight;
    }

    public Boolean itemExists(Order order, String orderId, String itemName) {
        for (Line line : this.lines) {
            if (order.getOrderId().getId().equals(orderId) && line.getLineId().getItemName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void addLine(Line line) { this.lines.add(line);}

    public Set<Line> getLines() {
        return lines;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Drone getDrone() { return drone; }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String displayOrder() {
        List<String> result=new ArrayList<>();
        result.add("orderID:" + orderId.getId());
        for (Line line : lines)
            result.add("item_name:" + line.getLineId().getItemName() + ",total_quantity:" + line.getQty()
                    + ",total_cost:" + line.getTotalPrice() + ",total_weight:" + line.getTotalWeight()
            );
        return String.join("\n",result);
    }

}
