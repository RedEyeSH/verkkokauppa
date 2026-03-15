package fi.metropolia.verkkokauppa.dto;

import java.math.BigDecimal;

public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer categoryId;
    private Integer supplierId;

    public ProductDto(
            String name,
            String description,
            BigDecimal price,
            Integer stockQuantity,
            Integer categoryId,
            Integer supplierId
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public ProductDto() {}

    // Getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
