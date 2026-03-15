package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.dto.ProductDto;
import fi.metropolia.verkkokauppa.entity.Product;
import fi.metropolia.verkkokauppa.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return service.getProduct(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductDto dto) {
        return service.createProduct(dto);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
    }
}