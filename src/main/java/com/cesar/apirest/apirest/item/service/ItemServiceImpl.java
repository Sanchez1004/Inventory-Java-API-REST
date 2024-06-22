package com.cesar.apirest.apirest.item.service;

import com.cesar.apirest.apirest.item.dto.ItemMapper;
import com.cesar.apirest.apirest.item.dto.ItemRequest;
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
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    /**
     * Constructs the ItemServiceImpl with the provided ItemRepository.
     *
     * @param itemRepository The repository responsible for data access operations on Item entities.
     */
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    /**
     * Retrieves all items.
     *
     * @return A list of all ItemEntity objects.
     */
    @Override
    public List<ItemRequest> getAllItems(String sortType) {
        Sort.Direction direction = getSortDirection(sortType);
        Sort sort = Sort.by(direction, "id");
        List<ItemEntity> itemList = itemRepository.findAll(sort);
        return itemList.stream().map(itemMapper::toItemDTO).toList();
    }

    @Override
    public List<String> getListOfItemNames() {
        return itemRepository.getALlItemNames();
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
    public ItemRequest findItemById(Long id) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(id);
        Optional<ItemRequest> itemDTO = itemEntity.map(itemMapper::toItemDTO);
        return itemDTO.orElseThrow(() -> new ItemException("Item not found with id: " + id));
    }

    private ItemEntity getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemException("Item not found with id: " + id));
    }


    /**
     * Creates a new item.
     *
     * @param itemRequest The ItemEntity object representing the item to be created.
     * @return The newly created ItemEntity.
     */
    @Override
    public ItemRequest createItem(ItemRequest itemRequest) {
        if (itemRequest == null) {
            throw new ItemException("The item cannot be empty");
        }

        ItemEntity itemEntity = itemMapper.toItemEntity(itemRequest);
        return itemMapper.toItemDTO(itemRepository.save(itemEntity));
    }

    /**
     * Updates an existing item.
     *
     * @param id             The ID of the item to be updated.
     * @param newItemDetails The updated details of the item.
     * @return The updated ItemEntity.
     */
    @Override
    public ItemRequest updateItem(Long id, ItemRequest newItemDetails) {
        ItemEntity existingItem = getItemById(id);

        if (newItemDetails.getName() != null) {
            existingItem.setName(newItemDetails.getName());
        }
        if (newItemDetails.getDescription() != null) {
            existingItem.setDescription(newItemDetails.getDescription());
        }
        if (newItemDetails.getPrice() != 0) {
            existingItem.setPrice(newItemDetails.getPrice());
        }

        return itemMapper.toItemDTO(itemRepository.save(existingItem));
    }

    /**
     * Retrieves an item entity by its name.
     *
     * @param name the name of the item to retrieve
     * @return the item entity with the specified name
     * @throws ItemException if the item with the specified name is not found in the item list
     */
    @Override
    public ItemEntity getItemByName(String name) {
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
