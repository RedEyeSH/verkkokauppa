package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.dto.OrderDto;
import fi.metropolia.verkkokauppa.dto.OrderItemDto;
import fi.metropolia.verkkokauppa.entity.Order;
import fi.metropolia.verkkokauppa.entity.OrderItem;
import fi.metropolia.verkkokauppa.entity.OrderItemKey;
import fi.metropolia.verkkokauppa.repository.OrderItemRepository;
import fi.metropolia.verkkokauppa.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Order createOrder(OrderDto dto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");

        Order savedOrder = orderRepository.save(order);

        for (OrderItemDto item : dto.getItems()) {

            OrderItem oi = new OrderItem();

            OrderItemKey key = new OrderItemKey();
            key.setOrderId(savedOrder.getId());
            key.setProductId(item.getProductId());

            oi.setId(key);
            oi.setQuantity(item.getQuantity());

            orderItemRepository.save(oi);
        }

        return savedOrder;
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}