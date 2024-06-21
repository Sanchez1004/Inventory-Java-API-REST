package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.order.dto.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/list-orders")
    public ResponseEntity<List<OrderRequest>> getAllOrders() {
        try {
            List<OrderRequest> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/find-order/{id}")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable String id) {
        try {
            OrderRequest order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/find-order-by-client-name/{clientName}")
    public ResponseEntity<List<OrderRequest>> getOrdersByClientName(@PathVariable String clientName) {
        try {
            List<OrderRequest> ordersList = orderService.searchOrdersByClientName(clientName);
            return new ResponseEntity<>(ordersList, HttpStatus.OK);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            OrderRequest createdOrder = orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update-order-items")
    public ResponseEntity<OrderRequest> addItemsToOrderById(@RequestBody Map<String, Integer> itemList, @RequestParam String id) {
        try {
            OrderRequest newOrder = orderService.addItemsToOrderById(itemList, id);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
