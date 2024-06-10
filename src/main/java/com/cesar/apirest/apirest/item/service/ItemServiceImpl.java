package com.cesar.apirest.apirest.service;

import com.cesar.apirest.apirest.entity.ItemEntity;
import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing Item entities.
 */
@Service("ItemService")
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * Constructs the ItemServiceImpl with the provided ItemRepository.
     *
     * @param itemRepository The repository responsible for data access operations on Item entities.
     */
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Retrieves all items.
     *
     * @return A list of all ItemEntity objects.
     */
    @Override
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The ItemEntity with the specified ID.
     */
    @Override
    public ItemEntity getItemById(Long id) {
        Optional<ItemEntity> optionalItem = itemRepository.findById(id);
        return optionalItem.orElseThrow(() -> new ItemException("Item not found with id: " + id));
    }


    /**
     * Creates a new item.
     *
     * @param item The ItemEntity object representing the item to be created.
     * @return The newly created ItemEntity.
     */
    @Override
    public ItemEntity createItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    /**
     * Updates an existing item.
     *
     * @param id             The ID of the item to be updated.
     * @param newItemDetails The updated details of the item.
     * @return The updated ItemEntity.
     */
    @Override
    public ItemEntity updateItem(Long id, ItemEntity newItemDetails) {
        ItemEntity existingItem = getItemById(id);
        existingItem.setName(newItemDetails.getName());
        existingItem.setDescription(newItemDetails.getDescription());
        existingItem.setPrice(newItemDetails.getPrice());
        return itemRepository.save(existingItem);
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to be deleted.
     * @return True if the item was successfully deleted, otherwise false.
     */
    @Override
    public boolean deleteItemById(Long id) {
        itemRepository.deleteById(id);
        return true;
    }
}
