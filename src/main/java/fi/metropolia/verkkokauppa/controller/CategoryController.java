package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.entity.ProductCategory;
import fi.metropolia.verkkokauppa.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductCategory> getCategories() {
        return service.getCategories();
    }

    @PostMapping
    public ProductCategory createCategory(@RequestBody ProductCategory category) {
        return service.createCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }
}