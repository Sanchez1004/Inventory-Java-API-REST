package com.cesar.apirest.apirest.item.controller;

import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.item.ItemRequest;
import com.cesar.apirest.apirest.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves all items.
     *
     * @return ResponseEntity containing a list of ItemEntity objects.
     * @throws ResponseStatusException if an error occurs while fetching all items.
     */
    @GetMapping
    public ResponseEntity<List<ItemRequest>> getAllItems(@RequestParam String sortType) {
        if (!"ASC".equalsIgnoreCase(sortType) && !"DESC".equalsIgnoreCase(sortType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sorting option. Valid options are 'ASC' or 'DESC'.");
        }

        try {
            List<ItemRequest> items = itemService.getAllItems(sortType);
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
    public ResponseEntity<ItemRequest> getItemById(@PathVariable Long id) {
        try {
            ItemRequest item = itemService.findItemById(id);
            return ResponseEntity.ok(item);
        } catch (ItemException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new item.
     *
     * @param itemRequest The ItemEntity representing the item to be created.
     * @return ResponseEntity containing the created ItemEntity.
     * @throws ResponseStatusException if an error occurs while creating the item.
     */
    @PostMapping
    public ResponseEntity<ItemRequest> createItem(@RequestBody ItemRequest itemRequest) {
        try {
            ItemRequest createdItem = itemService.createItem(itemRequest);
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
    public ResponseEntity<ItemRequest> updateItem(@PathVariable Long id, @RequestBody ItemRequest newItemDetails) {
        try {
            ItemRequest updatedItem = itemService.updateItem(id, newItemDetails);
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
