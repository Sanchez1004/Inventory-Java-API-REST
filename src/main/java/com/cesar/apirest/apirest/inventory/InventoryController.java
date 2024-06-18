package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    ResponseEntity<InventoryEntity> createItemInInventory(@RequestBody ItemQuantityRequest itemRequest) {
        try {
            InventoryEntity response = inventoryService.createItemInInventory(itemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
