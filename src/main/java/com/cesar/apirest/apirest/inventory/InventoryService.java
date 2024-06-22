package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.inventory.dto.InventoryRequest;
import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

import java.util.List;

public interface InventoryService {
    InventoryRequest findInventoryItemById(String id);
    InventoryRequest createItemInInventory(InventoryRequest itemRequest);
    InventoryRequest addStockToItemById(String id, int quantity);
    List<InventoryRequest> getInventoryByItemNameContaining(String keyword) throws InventoryException;
    void deductItem(ItemEntity item, int quantity) throws InventoryException;
    void isItemQuantityAvailable(ItemEntity item, int quantity) throws InventoryException;
}
