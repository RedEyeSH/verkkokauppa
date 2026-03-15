package fi.metropolia.verkkokauppa.repository;

import fi.metropolia.verkkokauppa.entity.OrderItem;
import fi.metropolia.verkkokauppa.entity.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
    List<OrderItem> findByIdOrderId(Integer orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.id.orderId = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Integer orderId);

}