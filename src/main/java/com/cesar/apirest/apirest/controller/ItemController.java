package com.cesar.apirest.apirest.controller;

import com.cesar.apirest.apirest.entity.ItemEntity;
import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            @ApiResponse(responseCode = "200"
                    , description = "Items found"
                    , content = @Content(schema = @Schema(type = "array"
                    , implementation = ItemEntity.class))),
            @ApiResponse(responseCode = "404"
                    , description = "Items not found")
    })
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        try {
            List<ItemEntity> items = itemService.getAllItems();
            if (items.isEmpty()) {
                return ResponseEntity.notFound().build(); // Build() used to end the request
            }
            return ResponseEntity.ok(items);
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching all items", e);
        }
    }

    @Operation(summary = "Get specified item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"
                    , description = "Item found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = ItemEntity.class))),

            @ApiResponse(responseCode = "404"
                    , description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
        try {
            ItemEntity item = itemService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (ItemException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Create an item with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"
                    , description = "Item successfully created"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = ItemEntity.class))),

            @ApiResponse(responseCode = "400"
                    , description = "Invalid item details provided"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "500"
                    , description = "Internal server error while creating the item"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity item) {
        try {
            ItemEntity createdItem = itemService.createItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (ItemException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Update an existing item with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully updated"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = ItemEntity.class))),

            @ApiResponse(responseCode = "404", description = "Item not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error while updating the item"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable Long id, @RequestBody ItemEntity newItemDetails) {
        try {
            ItemEntity updatedItem = itemService.updateItem(id, newItemDetails);
            return ResponseEntity.ok(updatedItem);
        } catch (ItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Delete an item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Item not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error while deleting the item"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = String.class)))
    })
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
