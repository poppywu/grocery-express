package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table(name = "Line")
@Entity
public class Line {
    @EmbeddedId
    private LineId lineId;
    private Integer qty;
    private Integer price;
    private Integer weight;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("orderId")
    @JoinColumns({@JoinColumn(name="order_id",referencedColumnName = "order_id"), @JoinColumn(name="store_name",referencedColumnName = "store_name")})
    private Order order;

    public Line() {}

    public Line(LineId lineId, Integer qty, Integer price, Integer weight, Order order) {
        this.lineId = lineId;
        this.qty = qty;
        this.price = price;
        this.weight = weight;
        this.order = order;
    }

    public void setOrder(Order order) { this.order = order; }

    public LineId getLineId() { return lineId; }

    public Integer getQty() { return qty; }

    public Integer getTotalPrice() { return price * qty; }

    public Integer getTotalWeight() { return weight * qty; }
}
