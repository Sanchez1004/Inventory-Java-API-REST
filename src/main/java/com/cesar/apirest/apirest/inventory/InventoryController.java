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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin/create")
    ResponseEntity<InventoryRequest> createItemInInventory(@RequestBody InventoryRequest inventoryRequest) {
        try {
            InventoryRequest response = inventoryService.createItemInInventory(inventoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    ResponseEntity<List<InventoryRequest>> findItemInInventoryByNameContaining(@RequestParam String name) {
        try {
            List<InventoryRequest> itemList = inventoryService.getInventoryByItemNameContaining(name);
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (InventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
