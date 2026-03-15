package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.dto.OrderDto;
import fi.metropolia.verkkokauppa.entity.Order;
import fi.metropolia.verkkokauppa.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto dto) {
        return service.createOrder(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
    }
}