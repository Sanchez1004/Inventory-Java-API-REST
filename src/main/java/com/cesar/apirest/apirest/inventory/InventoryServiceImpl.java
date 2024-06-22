package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.inventory.dto.InventoryRequest;
import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.inventory.dto.InventoryMapper;
import com.cesar.apirest.apirest.item.dto.ItemMapper;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    /**
     * Constructor for InventoryServiceImpl.
     *
     * @param inventoryRepository the repository for handling inventory entities
     * @param inventoryMapper     the mapper for converting inventory entities to DTOs and vice versa
     * @param itemService         the service for handling item operations
     * @param itemMapper          the mapper for converting item entities to DTOs and vice versa
     */
    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper, ItemService itemService, ItemMapper itemMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    /**
     * Retrieves an inventory item by its ID.
     *
     * @param id the ID of the inventory item to retrieve
     * @return the inventory request associated with the specified ID
     * @throws InventoryException if no inventory item is found with the specified ID
     */
    @Override
    public InventoryRequest findInventoryItemById(String id) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findById(id);
        Optional<InventoryRequest> inventoryDTO = inventoryEntity.map(inventoryMapper::toDTO);
        return inventoryDTO.orElseThrow(() -> new InventoryException("Item not found with id: " + id));
    }

    /**
     * Retrieves an inventory item entity by its ID.
     *
     * @param id the ID of the inventory item entity to retrieve
     * @return the inventory item entity with the specified ID
     * @throws InventoryException if no inventory item entity is found with the specified ID
     */
    private InventoryEntity getInventoryItemById(String id) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findById(id);
        return inventoryEntity.orElseThrow(() -> new InventoryException("Item not found with id: " + id));
    }

    /**
     * Creates an item in the inventory.
     *
     * @param inventoryRequest the request containing details of the item to be created in the inventory
     * @return the inventory request for the created item
     * @throws InventoryException if the item in the request is empty or if the quantity is zero or negative
     */
    @Override
    public InventoryRequest createItemInInventory(InventoryRequest inventoryRequest) {
        if (inventoryRequest.getItem() == null) {
            throw new InventoryException("The item cannot be empty");
        }

        if (inventoryRequest.getQuantity() <= 0) {
            throw new InventoryException("The quantity cannot be 0 o negative");
        }

        itemService.createItem(itemMapper.toItemDTO(inventoryRequest.getItem()));

        InventoryEntity updatedInventory = inventoryMapper.toEntity(inventoryRequest);

        return inventoryMapper.toDTO(inventoryRepository.save(updatedInventory));
    }

    /**
     * Adds stock to an item in the inventory by its ID.
     *
     * @param id       the ID of the item to which stock is to be added
     * @param quantity the quantity of stock to be added
     * @return the inventory request for the updated item
     * @throws InventoryException if the quantity is zero or negative
     */
    @Override
    public InventoryRequest addStockToItemById(String id, int quantity) {
        InventoryEntity inventoryEntity = getInventoryItemById(id);
        if (quantity <= 0) {
            throw new InventoryException("Quantity cannot be negative o zero");
        }

        inventoryEntity.setQuantity(quantity);
        inventoryRepository.save(inventoryEntity);
        return inventoryMapper.toDTO(inventoryEntity);
    }

    /**
     * Retrieves a list of inventory requests for items whose names contain the specified keyword.
     *
     * @param keyword the keyword to search for in the item names
     * @return a list of inventory requests for items whose names contain the keyword
     * @throws InventoryException if an error occurs while retrieving the inventory items
     */
    @Override
    public List<InventoryRequest> getInventoryByItemNameContaining(String keyword) throws InventoryException {
        String regex = ".*" + keyword + ".*";
        List<InventoryEntity> inventoryEntityList = inventoryRepository.findByItemNameRegex(regex);
        return inventoryEntityList.stream()
                .map(inventoryMapper::toDTO)
                .toList();
    }

    /**
     * Checks if the specified quantity of an item is available in the inventory.
     *
     * @param item     the item entity to check availability for
     * @param quantity the quantity of the item to check availability for
     * @throws InventoryException if the item is not found in the inventory or if the quantity is insufficient
     */
    @Override
    public void isItemQuantityAvailable(ItemEntity item, int quantity) throws InventoryException {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemEntity(item);

        if (inventoryEntity == null) {
            throw new InventoryException("Item not found in inventory: " + item.getName());
        }

        if (inventoryEntity.getQuantity() == 0 || inventoryEntity.getQuantity() < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + inventoryEntity.getItemName());
        }
    }

    /**
     * Deducts the specified quantity of an item from the inventory.
     *
     * @param item     the item entity to deduct from the inventory
     * @param quantity the quantity of the item to deduct
     * @throws InventoryException if the item is not found in the inventory or if the quantity is insufficient
     */
    @Override
    public void deductItem(ItemEntity item, int quantity) throws InventoryException {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemEntity(item);
        if (inventoryEntity == null || inventoryEntity.getQuantity() < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + item.getName());
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() - quantity);
        inventoryRepository.save(inventoryEntity);
    }
}
