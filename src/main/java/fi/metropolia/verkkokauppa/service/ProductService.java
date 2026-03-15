package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.dto.ProductDto;
import fi.metropolia.verkkokauppa.entity.Product;
import fi.metropolia.verkkokauppa.entity.ProductCategory;
import fi.metropolia.verkkokauppa.entity.Supplier;
import fi.metropolia.verkkokauppa.repository.ProductRepository;
import fi.metropolia.verkkokauppa.repository.CategoryRepository;
import fi.metropolia.verkkokauppa.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final  SupplierRepository supplierRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Integer id) {
        Product p = productRepository.findById(id).orElseThrow();

        System.out.println(p.getCategory());
        System.out.println(p.getSupplier());

        System.out.println("Product ID: " + p.getId());
        if (p.getCategory() != null) {
            System.out.println("Category ID: " + p.getCategory().getId());
            System.out.println("Category Name: " + p.getCategory().getName());
        } else {
            System.out.println("Category is null!");
        }
//        return productRepository.findById(id).orElseThrow();
        return p;
    }

//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }

    public Product createProduct(ProductDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        ProductCategory category =
                categoryRepository.findById(dto.getCategoryId()).orElseThrow();

        product.setCategory(category);

        Supplier supplier =
                supplierRepository.findById(dto.getSupplierId()).orElseThrow();

        product.setSupplier(supplier);

        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product product) {
        Product existing = getProduct(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStockQuantity(product.getStockQuantity());

        return productRepository.save(existing);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}