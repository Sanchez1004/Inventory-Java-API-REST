package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

public interface InventoryService {
    InventoryEntity createItemInInventory(ItemQuantityRequest itemRequest);
    void deductItem(ItemEntity item, int quantity) throws InventoryException;
    void isItemQuantityAvailable(ItemEntity item, int quantity);
}
