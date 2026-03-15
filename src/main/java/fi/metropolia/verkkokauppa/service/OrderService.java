package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.dto.OrderDto;
import fi.metropolia.verkkokauppa.dto.OrderItemDto;
import fi.metropolia.verkkokauppa.entity.Order;
import fi.metropolia.verkkokauppa.entity.OrderItem;
import fi.metropolia.verkkokauppa.entity.OrderItemKey;
import fi.metropolia.verkkokauppa.entity.Product;
import fi.metropolia.verkkokauppa.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Order createOrder(OrderDto dto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");

        order.setCustomer(customerRepository.findById(dto.getCustomerId()).orElseThrow());
        order.setShippingAddress(customerAddressRepository.findById(dto.getShippingAddressId()).orElseThrow());

        Order savedOrder = orderRepository.save(order);

        for (OrderItemDto itemDto : dto.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(savedOrder);

            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow();
            item.setProduct(product);

            item.setQuantity(itemDto.getQuantity());
            item.setUnitPrice(product.getPrice());

            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    public List<OrderItem> getOrderItems(Integer orderId) {
//        return orderItemRepository.findByIdOrderId(orderId);
        System.out.println(orderId);
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public void addItem(Integer orderId, OrderItemDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = new OrderItem();

        item.setOrder(order);
        item.setProduct(product);

        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(product.getPrice());

        orderItemRepository.save(item);
    }

    @Transactional
    public Order updateStatus(Integer id, String status) {

        Order order = orderRepository.findById(id).orElseThrow();

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public Double getOrderTotal(Integer orderId) {
        List<OrderItem> items = orderItemRepository.findByIdOrderId(orderId);

        System.out.println(items);

        double total = 0;

        for(OrderItem i : items) {
            total += i.getQuantity() * i.getUnitPrice().doubleValue();
        }
        return total;
    }

    @Transactional
    public void deleteItem(Integer orderId, Integer productId) {
        OrderItemKey key = new OrderItemKey();
        key.setOrderId(orderId);
        key.setProductId(productId);

        orderItemRepository.deleteById(key);
    }
}