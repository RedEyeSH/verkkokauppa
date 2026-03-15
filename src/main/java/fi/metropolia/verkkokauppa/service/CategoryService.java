package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.entity.Customer;
import fi.metropolia.verkkokauppa.entity.ProductCategory;
import fi.metropolia.verkkokauppa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ProductCategory> getCategories() {
        return categoryRepository.findAll();
    }

    public ProductCategory getCategory(Integer id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public ProductCategory createCategory(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public ProductCategory updateCategory(Integer id, ProductCategory category) {
        ProductCategory existing = getCategory(id);

        existing.setName(category.getName());
        existing.setDescription((category.getDescription()));

        return categoryRepository.save(existing);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}