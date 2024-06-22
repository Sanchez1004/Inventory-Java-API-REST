package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.inventory.dto.InventoryRequest;
import com.cesar.apirest.apirest.exception.InventoryException;
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

/**
 * Controller for handling inventory-related requests.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Constructor for InventoryController.
     *
     * @param inventoryService the service for handling inventory operations
     */
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Creates an item in the inventory.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param inventoryRequest the request containing details of the item to be created in the inventory
     * @return a ResponseEntity containing the created inventory request and HTTP status OK
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<InventoryRequest> createItemInInventory(@RequestBody InventoryRequest inventoryRequest) {
        try {
            InventoryRequest response = inventoryService.createItemInInventory(inventoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Finds items in the inventory by name containing the specified string.
     * Accessible to users with either 'ADMIN' or 'USER' role.
     *
     * @param name the string to be contained in the item name
     * @return a ResponseEntity containing a list of inventory requests matching the search criteria and HTTP status OK
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<InventoryRequest>> findItemInInventoryByNameContaining(@RequestParam String name) {
        try {
            List<InventoryRequest> itemList = inventoryService.getInventoryByItemNameContaining(name);
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (InventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Adds stock to an item in the inventory by its ID.
     * Accessible only to users with the 'ADMIN' role.
     *
     * @param id       the ID of the item to which stock is to be added
     * @param quantity the quantity of stock to be added
     * @return a ResponseEntity containing the updated inventory request and HTTP status CREATED
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/add-stock/{id}")
    public ResponseEntity<InventoryRequest> addStockToItemById(@PathVariable String id, @RequestParam int quantity) {
        try {
            InventoryRequest inventoryRequest = inventoryService.addStockToItemById(id, quantity);
            return new ResponseEntity<>(inventoryRequest, HttpStatus.CREATED);
        } catch (InventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
