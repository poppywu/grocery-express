package cs6310.backend.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemId implements Serializable {
    private String itemName;
    private String storeName;

    public ItemId() {
    }

    public ItemId(String itemName, String storeName) {
        this.itemName = itemName;
        this.storeName = storeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
