package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}