package edu.gatech.cs6310;

import java.util.ArrayList;

public class Order {
    private final String id;
    private final String customerId;
    private final Integer droneId;
    private final ArrayList<Line> lines;

    public Order(String id, String cid, Integer did) {
        this.id = id;
        this.customerId = cid;
        this.droneId = did;
        this.lines = new ArrayList<>();
    }

    public String getId() { return id; }

    public Integer getDroneId() { return droneId; }

    public String getCustomerId() { return this.customerId; }

    public int getCost() {
        int cost = 0;
        for(Line line: this.lines)
            cost += line.getTotalPrice();
        return cost;
    }

    public int getWeight() {
        int weight = 0;
        for(Line line: this.lines)
            weight += line.getTotalWeight();
        return weight;
    }

    public Boolean itemExists(Order order, String orderId, String itemName) {
        for(Line line: this.lines) {
            if(order.getId().equals(orderId) && line.getItemName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void addLine(Line line) { this.lines.add(line); }

    public ArrayList<Line> getLines() { return lines; }

    public void displayOrder() {
        System.out.println("orderID:" + id);
        for(Line line: lines)
            System.out.println("item_name:" + line.getItemName() + ",total_quantity:" + line.getQty()
                    + ",total_cost:" + line.getTotalPrice() + ",total_weight:" + line.getTotalWeight()
            );
    }
}
