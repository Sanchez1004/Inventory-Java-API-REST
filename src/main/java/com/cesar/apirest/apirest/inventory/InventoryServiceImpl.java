package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemService itemService;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ItemService itemService) {
        this.inventoryRepository = inventoryRepository;
        this.itemService = itemService;
    }


    @Override
    public void isItemQuantityAvailable(ItemEntity item, int quantity) {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemsInventoryKey(item);

        if (inventoryEntity == null) {
            throw new InventoryException("Item not found in inventory: " + item.getName());
        }

        Integer availableQuantity = inventoryEntity.getItemsInventory().get(item);
        if (availableQuantity == null || availableQuantity < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + item.getName());
        }
    }


    @Override
    public InventoryEntity createItemInInventory(ItemQuantityRequest itemRequest) {
        if (itemRequest.getItem() == null) {
           throw new InventoryException("The item cannot be empty");
        }

        itemService.createItem(itemRequest.getItem());
        Map<ItemEntity, Integer> newItem = new HashMap<>();
        newItem.put(itemRequest.getItem(), itemRequest.getQuantity());

        InventoryEntity updatedInventory = InventoryEntity
                .builder()
                .itemsInventory(newItem)
                .build();

        inventoryRepository.save(updatedInventory);
        return updatedInventory;
    }

    public void deductItem(ItemEntity item, int quantity) throws InventoryException {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemsInventoryKey(item);
        if (inventoryEntity == null || inventoryEntity.getItemsInventory().get(item) < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + item.getName());
        }

        inventoryEntity.getItemsInventory().put(item, inventoryEntity.getItemsInventory().get(item) - quantity);
        inventoryRepository.save(inventoryEntity);
    }
}
