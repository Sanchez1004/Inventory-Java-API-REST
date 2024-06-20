package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.inventory.dto.InventoryDTO;
import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

import java.util.List;

public interface InventoryService {
    InventoryDTO createItemInInventory(InventoryDTO itemRequest);
    List<InventoryDTO> getInventoryByItemNameContaining(String keyword) throws InventoryException;
    void deductItem(ItemEntity item, int quantity) throws InventoryException;
    void isItemQuantityAvailable(ItemEntity item, int quantity) throws InventoryException;
}
