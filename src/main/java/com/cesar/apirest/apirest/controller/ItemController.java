package com.cesar.apirest.apirest.controller;

import com.cesar.apirest.apirest.entities.Item;
import com.cesar.apirest.apirest.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> GetAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item GetItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }

    @PostMapping
    public Item CreateItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item UpdateItem(@PathVariable Long id, @RequestBody Item detailsItem) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));

        item.setName(detailsItem.getName());
        item.setDescription(detailsItem.getDescription());
        item.setPrice(detailsItem.getPrice());

        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public String DeleteItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));

        itemRepository.delete(item);
        return "Item with id: " + id + " deleted successfully";
    }
}
