package fi.metropolia.verkkokauppa.dto;

import java.util.List;

public class OrderResponseDto {
    private Integer orderId;
    private String status;
    private List<OrderItemDto> items;

    public OrderResponseDto(Integer orderId, String status, List<OrderItemDto> items) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }

    // Getters & setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}