package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.dto.OrderDto;
import fi.metropolia.verkkokauppa.dto.OrderItemDto;
import fi.metropolia.verkkokauppa.dto.OrderStatusDto;
import fi.metropolia.verkkokauppa.entity.Order;
import fi.metropolia.verkkokauppa.entity.OrderItem;
import fi.metropolia.verkkokauppa.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Integer id) {
        return service.getOrder(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto dto) {
        return service.createOrder(dto);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Integer id, @RequestBody OrderStatusDto dto) {
        return service.updateStatus(id, dto.getStatus());
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> getOrderItems(@PathVariable Integer id) {
        return service.getOrderItems(id);
    }

    @PostMapping("/{id}/items")
    public void addItem(@PathVariable Integer id, @RequestBody OrderItemDto dto) {
        service.addItem(id, dto);
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    public void deleteItem(@PathVariable Integer orderId, @PathVariable Integer productId) {
        service.deleteItem(orderId, productId);
    }

    @GetMapping("/{id}/total")
    public Double getOrderTotal(@PathVariable Integer id) {
        return service.getOrderTotal(id);
    }
}