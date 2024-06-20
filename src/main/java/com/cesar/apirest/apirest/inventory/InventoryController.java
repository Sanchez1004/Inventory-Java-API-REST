package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    ResponseEntity<InventoryDTO> createItemInInventory(@RequestBody InventoryDTO inventoryRequest) {
        try {
            InventoryDTO response = inventoryService.createItemInInventory(inventoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    ResponseEntity<List<InventoryDTO>> findItemInInventoryByNameContaining(@RequestParam String name) {
        try {
            List<InventoryDTO> itemList = inventoryService.getInventoryByItemNameContaining(name);
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (InventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);gi
        }
    }

}
