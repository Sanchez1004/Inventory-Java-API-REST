package com.cesar.apirest.apirest.order.controller;

import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.order.service.OrderService;
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

/**
 * Controller for handling order-related requests.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructor for OrderController.
     *
     * @param orderService the service for handling orders
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves a list of all orders.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @return a ResponseEntity containing the list of orders and HTTP status OK
     */
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

    /**
     * Retrieves an order by its ID.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param id the ID of the order
     * @return a ResponseEntity containing the order and HTTP status OK
     */
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

    /**
     * Retrieves orders by the client's name.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param clientName the name of the client
     * @return a ResponseEntity containing the list of orders and HTTP status OK
     */
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

    /**
     * Creates a new order.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param orderRequest the order request object containing order details
     * @return a ResponseEntity containing the created order and HTTP status CREATED
     */
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

    /**
     * Adds items to an existing order by its ID.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param itemList the map of items to be added with their quantities
     * @param id the ID of the order to which items are to be added
     * @return a ResponseEntity containing the updated order and HTTP status CREATED
     */
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
