package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = {"category", "supplier"})
    Optional<Product> findById(Integer id);
}