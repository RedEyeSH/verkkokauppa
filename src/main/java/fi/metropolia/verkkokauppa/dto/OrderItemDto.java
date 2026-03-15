package fi.metropolia.verkkokauppa.dto;

public class OrderItemDto {
    private Integer productId;
    private Integer quantity;

    public OrderItemDto(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderItemDto() {}

    // Getters & setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
