package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.dto.ProductCatalogDto;
import fi.metropolia.verkkokauppa.dto.ProductDto;
import fi.metropolia.verkkokauppa.entity.Product;
import fi.metropolia.verkkokauppa.entity.ProductCategory;
import fi.metropolia.verkkokauppa.entity.Supplier;
import fi.metropolia.verkkokauppa.repository.ProductRepository;
import fi.metropolia.verkkokauppa.repository.CategoryRepository;
import fi.metropolia.verkkokauppa.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow();
    }

//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }

    @Transactional
    public Product createProduct(ProductDto dto) {
        // 1. Debug-tuloste: nähdään mitä dataa Postmanista tulee sisään
        System.out.println("Yritetään luoda tuotetta: " + dto.getName());
        System.out.println("Kategoria-ID: " + dto.getCategoryId());
        System.out.println("Toimittaja-ID: " + dto.getSupplierId());

        if (dto.getCategoryId() == null || dto.getSupplierId() == null) {
            throw new RuntimeException("Kategoria-ID tai Toimittaja-ID puuttuu pyynnöstä!");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        // 2. Haetaan kategoria ja heitetään selkeä virhe jos ei löydy
        ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategoriaa ei löytynyt ID:llä: " + dto.getCategoryId()));
        product.setCategory(category);

        // 3. Haetaan toimittaja ja heitetään selkeä virhe jos ei löydy
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Toimittajaa ei löytynyt ID:llä: " + dto.getSupplierId()));
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

    public List<ProductCatalogDto> getProductCatalog() {
        return productRepository.findAllProductsForCatalog();
    }
}