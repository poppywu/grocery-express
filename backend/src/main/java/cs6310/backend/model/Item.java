package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "item")
public class Item {
    @EmbeddedId
    private ItemId itemId;
    private Integer weight;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeName")
    @JoinColumn(name = "store_name", referencedColumnName = "name")
    private Store store;

    public Item(ItemId itemId, Integer weight,Store store) {
        this.itemId = itemId;
        this.weight = weight;
        this.store=store;
    }

    public Item() {}

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String displayItem() { return itemId.getItemName() + "," + weight; }

    public Store getStore() { return store; }

    public void setStore(Store store) { this.store = store; }
}
