package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.order.OrderException;

public interface InventoryService {
    InventoryEntity updateInventoryItemById(InventoryEntity inventory, String id);
    void updateInventory(InventoryEntity inventoryEntity);
    public boolean isItemAvailable(ItemEntity item, int quantity);
    public void deductItem(ItemEntity item, int quantity) throws InventoryException;
}
