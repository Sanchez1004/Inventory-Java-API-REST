package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Override
    public InventoryEntity updateInventoryItemById(InventoryEntity inventory, String id) {
        return null;
    }

    @Override
    public void updateInventory(InventoryEntity inventoryEntity) {
        inventoryRepository.save(inventoryEntity);
    }

    @Override
    public void isItemQuantityAvailable(ItemEntity item, int quantity) {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemsInventoryKey(item);
        if (inventoryEntity == null && inventoryEntity.getItemsInventory().get(item) < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + item.getName());
        }
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
