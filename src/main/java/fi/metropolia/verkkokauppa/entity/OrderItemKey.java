package fi.metropolia.verkkokauppa.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderItemKey implements Serializable {
    private Integer orderId;
    private Integer productId;

    // Getters & setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}