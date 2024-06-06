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
        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new ItemException("An error occurred while fetching all items", e);
        }
    }

    @Override
    public ItemEntity getItemById(Long id) {
        try {
            Optional<ItemEntity> item = itemRepository.findById(id);
            return item.orElseThrow(() -> new ItemException("Item not found with id: " + id));
        } catch (Exception e) {
            throw new ItemException("An error occurred while fetching the item with id: " + id, e);
        }
    }

    public ItemEntity createItem(ItemEntity item) {
        try {
            Optional<ItemEntity> savedItem = Optional.of(itemRepository.save(item));
            return savedItem.orElseThrow(() -> new ItemException("Failed to create item"));
        } catch (Exception e) {
            throw new ItemException("An error occurred while creating the item", e);
        }
    }

    public ItemEntity updateItem(Long id, ItemEntity newItemDetails) {
        try {
            ItemEntity existingItem = getItemById(id);

            existingItem.setName(newItemDetails.getName());
            existingItem.setDescription(newItemDetails.getDescription());
            existingItem.setPrice(newItemDetails.getPrice());

            return itemRepository.save(existingItem);
        } catch (ItemException e) {
            throw e;
        } catch (Exception e) {
            throw new ItemException("An error occurred while updating the item", e);
        }
    }

    public boolean deleteItemById(Long id) {
        try {
            ItemEntity existingItem = getItemById(id);
            itemRepository.delete(existingItem);
            return true;
        } catch (Exception e) {
            throw new ItemException("An error occurred while deleting the item with id: " + id, e);
        }
    }
}
