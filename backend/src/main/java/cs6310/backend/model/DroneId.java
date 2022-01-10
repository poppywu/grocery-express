package cs6310.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DroneId implements Serializable {
    @Column(name = "drone_id")
    private Integer id;
    private String storeName;

    public DroneId() {
    }

    public DroneId(Integer id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
