package edu.gatech.cs6310;

public class Line {
    private final String itemName;
    private final Integer qty;
    private final Integer price;
    private final Integer weight;

    public Line(String itemName, Integer qty, Integer price, Integer weight) {
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.weight = weight;
    }

    public String getItemName() { return itemName; }

    public Integer getQty() { return qty; }

    public Integer getTotalPrice() {
        return price*qty;
    }

    public Integer getTotalWeight() {
        return weight*qty;
    }
}
