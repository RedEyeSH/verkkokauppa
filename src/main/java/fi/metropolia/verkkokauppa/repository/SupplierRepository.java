package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}