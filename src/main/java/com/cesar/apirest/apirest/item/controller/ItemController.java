package com.cesar.apirest.apirest.item.controller;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.exception.ItemException;
import com.cesar.apirest.apirest.item.service.ItemService;
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

    public ItemController(@Qualifier("ItemService") ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves all items.
     *
     * @return ResponseEntity containing a list of ItemEntity objects.
     * @throws ResponseStatusException if an error occurs while fetching all items.
     */
    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAllItems(@RequestParam String sortType) {
        if (!"ASC".equalsIgnoreCase(sortType) && !"DESC".equalsIgnoreCase(sortType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sorting option. Valid options are 'ASC' or 'DESC'.");
        }

        try {
            List<ItemEntity> items = itemService.getAllItems(sortType);
            return ResponseEntity.ok(items);
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching all items", e);
        }
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return ResponseEntity containing the retrieved ItemEntity.
     * @throws ResponseStatusException if the item with the given ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
        try {
            ItemEntity item = itemService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (ItemException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new item.
     *
     * @param item The ItemEntity representing the item to be created.
     * @return ResponseEntity containing the created ItemEntity.
     * @throws ResponseStatusException if an error occurs while creating the item.
     */
    @PostMapping
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity item) {
        try {
            ItemEntity createdItem = itemService.createItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create item", e);
        }
    }

    /**
     * Updates an existing item.
     *
     * @param id             The ID of the item to update.
     * @param newItemDetails The updated details of the item.
     * @return ResponseEntity containing the updated ItemEntity.
     * @throws ResponseStatusException if the item with the given ID is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable Long id, @RequestBody ItemEntity newItemDetails) {
        try {
            ItemEntity updatedItem = itemService.updateItem(id, newItemDetails);
            return ResponseEntity.ok(updatedItem);
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id, e);
        }
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @return ResponseEntity indicating success or failure of the deletion operation.
     * @throws ResponseStatusException if an error occurs while deleting the item.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItemById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id " + id, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the item", e);
        }
    }
}
