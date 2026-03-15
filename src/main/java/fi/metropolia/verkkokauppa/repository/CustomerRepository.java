package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}