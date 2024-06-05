package com.cesar.apirest.apirest.controller;

import com.cesar.apirest.apirest.entity.Item;
import com.cesar.apirest.apirest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
//    public List<Item> GetAllItems() {
//        return itemRepository.findAll();
//    }
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{id}")
//    public Item GetItemById(@PathVariable Long id) {
//        return itemRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
//    }
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping
//    public Item CreateItem(@RequestBody Item item) {
//        return itemRepository.save(item);
//    }
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.createItem(item), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
//    public Item UpdateItem(@PathVariable Long id, @RequestBody Item detailsItem) {
//        Item item = itemRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
//
//        item.setName(detailsItem.getName());
//        item.setDescription(detailsItem.getDescription());
//        item.setPrice(detailsItem.getPrice());
//
//        return itemRepository.save(item);
//    }
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item detailsItem) {
        return ResponseEntity.ok(itemService.updateItem(id, detailsItem));
    }

    @DeleteMapping("/{id}")
//    public String DeleteItem(@PathVariable Long id) {
//        Item item = itemRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
//
//        itemRepository.delete(item);
//        return "Item with id: " + id + " deleted successfully";
//    }
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.deleteItemById(id));
    }
}
