package cs6310.backend.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class LineId implements Serializable {
    @Embedded
    private OrderId orderId;
    private String itemName;

    public LineId() {}

    public LineId(OrderId orderId, String itemName) {
        this.orderId = orderId;
        this.itemName = itemName;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }
}
