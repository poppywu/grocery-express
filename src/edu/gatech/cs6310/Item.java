package edu.gatech.cs6310;

public class Item {
    private final String name;
    private final Integer weight;

    public Item(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() { return weight; }

    public void displayItem() {
        System.out.println(name + "," + weight);
    }
}
