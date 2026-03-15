package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.dto.ProductCatalogDto;
import fi.metropolia.verkkokauppa.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = {"category", "supplier"})
    Optional<Product> findById(Integer id);

    @EntityGraph(attributePaths = {"category", "supplier"})
    List<Product> findAll();

    @Query("SELECT new fi.metropolia.verkkokauppa.dto.ProductCatalogDto(" +
            "p.id, p.name, p.description, p.price, p.stockQuantity, c.name, s.name) " +
            "FROM Product p " +
            "LEFT JOIN p.category c " +
            "LEFT JOIN p.supplier s")
    List<ProductCatalogDto> findAllProductsForCatalog();
}