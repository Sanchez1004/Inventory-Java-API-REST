package com.cesar.apirest.apirest.service;

import com.cesar.apirest.apirest.entity.ItemEntity;
import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ItemService")
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public ItemEntity getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemException("Item not found with id: " + id));
    }

    public ItemEntity createItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public ItemEntity updateItem(Long id, ItemEntity newItemDetails) {
        ItemEntity existingItem = getItemById(id);

        existingItem.setName(newItemDetails.getName());
        existingItem.setDescription(newItemDetails.getDescription());
        existingItem.setPrice(newItemDetails.getPrice());

        return itemRepository.save(existingItem);
    }

    public boolean deleteItemById(Long id) {
        Optional<ItemEntity> existingItem = itemRepository.findById(id);

        if (!existingItem.isPresent()) {
            throw new ItemException("Item not found with id " + id);
        }

        try {
            itemRepository.delete(existingItem.get());
        } catch (Exception e) {
            throw new ItemException("An error occurred while deleting the item with id: " + id, e);
        }

        return !itemRepository.existsById(id); // Checks if the element was successfully deleted
    }

}
