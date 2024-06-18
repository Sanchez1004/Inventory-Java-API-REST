package com.cesar.apirest.apirest.item.service;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.exception.ItemException;
import com.cesar.apirest.apirest.item.repository.ItemRepository;
import org.springframework.data.domain.Sort;
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
    public List<ItemEntity> getAllItems(String sortType) {
        Sort.Direction direction = getSortDirection(sortType);
        Sort sort = Sort.by(direction, "id");
        return itemRepository.findAll(sort);
    }

    @Override
    public List<String> getListOfItemNames() {
        return itemRepository.findAllItemNames();
    }

    private Sort.Direction getSortDirection(String sortType) {
        if ("ASC".equalsIgnoreCase(sortType)) {
            return Sort.Direction.ASC;
        } else if ("DESC".equalsIgnoreCase(sortType)) {
            return Sort.Direction.DESC;
        } else {
            // Default to ASC if sortType is not recognized
            return Sort.Direction.ASC;
        }
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
        if(itemRepository.existsById(id)) {
            ItemEntity item = ItemEntity
                    .builder()
                    .id(id)
                    .name(newItemDetails.getName())
                    .description(newItemDetails.getDescription())
                    .price(newItemDetails.getPrice())
                    .build();
            return itemRepository.save(item);
        }
        throw new ItemException("Item not found with id: " + id);
    }

    @Override
    public ItemEntity itemExistsByName(String name) {
        ItemEntity item = itemRepository.findByName(name);
        if (item == null) {
            throw new ItemException("Item: " + name + ", not found in item list");
        }
        return item;
    }


    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to be deleted.
     */
    @Override
    public void deleteItemById(Long id) {
        ItemEntity itemToDelete = getItemById(id);
        itemRepository.deleteById(itemToDelete.getId());
    }
}
