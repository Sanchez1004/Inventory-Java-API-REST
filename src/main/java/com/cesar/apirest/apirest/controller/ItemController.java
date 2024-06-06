package com.cesar.apirest.apirest.controller;

import com.cesar.apirest.apirest.entity.ItemEntity;
import com.cesar.apirest.apirest.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    //Using @Qualifier allow us to choose which implementation to use depending on the name of the class @ServiceName
    public ItemController(@Qualifier("ItemService") ItemService itemService) {
        this.itemService = itemService;
    }


    @Operation(summary = "Get all items list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found"),
            @ApiResponse(responseCode = "404", description = "Items not found")

    })
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "Get specified item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @Operation(summary = "Create item receiving the details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully created"),
            @ApiResponse(responseCode = "404", description = "Error creating the item")
    })
    @PostMapping
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity item) {
        return new ResponseEntity<>(itemService.createItem(item), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable Long id, @RequestBody ItemEntity detailsItem) {
        return ResponseEntity.ok(itemService.updateItem(id, detailsItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        try {
            boolean isDeleted = itemService.deleteItemById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Item deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the item");
        }
    }
}
