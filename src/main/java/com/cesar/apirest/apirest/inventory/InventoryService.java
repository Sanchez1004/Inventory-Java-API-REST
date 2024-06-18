package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

public interface InventoryService {
    InventoryEntity updateInventoryItemById(InventoryEntity inventory, String id);
    void updateInventory(InventoryEntity inventoryEntity);
    void deductItem(ItemEntity item, int quantity) throws InventoryException;
    void isItemQuantityAvailable(ItemEntity item, int quantity);
}
