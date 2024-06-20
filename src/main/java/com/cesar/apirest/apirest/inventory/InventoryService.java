package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

public interface InventoryService {
    InventoryDTO createItemInInventory(InventoryDTO itemRequest);
    InventoryDTO getInventoryByItemName(String itemName) throws InventoryException;
    void deductItem(ItemEntity item, int quantity) throws InventoryException;
    void isItemQuantityAvailable(ItemEntity item, int quantity) throws InventoryException;
}
