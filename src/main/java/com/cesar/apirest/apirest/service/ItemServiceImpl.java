package com.cesar.apirest.apirest.service;

import com.cesar.apirest.apirest.entity.Item;
import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemException("Item was not found with id: " + id));
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item newItemDetails) {
        Item existingItem = getItemById(id);
        
        existingItem.setName(newItemDetails.getName());
        existingItem.setDescription(newItemDetails.getDescription());
        existingItem.setPrice(newItemDetails.getPrice());

        return itemRepository.save(existingItem);
    }

    public String deleteItemById(Long id) {
        Item existingItem = getItemById(id);

        itemRepository.delete(existingItem);
        return ("Item: " + existingItem.getName() +", successfully deleted with id: " + id);
    }
}
