package fi.metropolia.verkkokauppa.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @EmbeddedId
    private OrderItemKey id;

    private Integer quantity;
    private BigDecimal unitPrice;

    // Getters & setters
    public OrderItemKey getId() {
        return id;
    }

    public void setId(OrderItemKey id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}