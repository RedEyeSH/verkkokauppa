package fi.metropolia.verkkokauppa.service;

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

    public ProductCategory createCategory(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}