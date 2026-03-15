package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.OrderItem;
import fi.metropolia.verkkokauppa.entity.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}