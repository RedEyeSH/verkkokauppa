package fi.metropolia.verkkokauppa.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Integer customerId;
    private Integer shippingAddressId;
    private List<OrderItemDto> items;

    public OrderDto(Integer customerId, Integer shippingAddressId, List<OrderItemDto> items) {
        this.customerId = customerId;
        this.shippingAddressId = shippingAddressId;
        this.items = items;
    }

    // Getters & setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Integer shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
