package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import org.springframework.stereotype.Service;

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
        InventoryEntity inventoryEntity = inventoryRepository.findByItemEntity(item);

        if (inventoryEntity == null) {
            throw new InventoryException("Item not found in inventory: " + item.getName());
        }

        if (inventoryEntity.getQuantity() == 0 || inventoryEntity.getQuantity() < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + inventoryEntity.getItemName());
        }
    }


    @Override
    public InventoryEntity createItemInInventory(InventoryEntity inventoryRequest) {
        if (inventoryRequest.getItem() == null) {
           throw new InventoryException("The item cannot be empty");
        }

        if(inventoryRequest.getQuantity() <= 0) {
            throw new InventoryException("The quantity cannot be 0 o negative");
        }

        itemService.createItem(inventoryRequest.getItem());

        InventoryEntity updatedInventory = InventoryEntity
                .builder()
                .quantity(inventoryRequest.getQuantity())
                .item(inventoryRequest.getItem())
                .build();

        inventoryRepository.save(updatedInventory);
        return updatedInventory;
    }

    public void deductItem(ItemEntity item, int quantity) throws InventoryException {
        InventoryEntity inventoryEntity = inventoryRepository.findByItemEntity(item);
        if (inventoryEntity == null || inventoryEntity.getQuantity() < quantity) {
            throw new InventoryException("Insufficient inventory for item: " + item.getName());
        }

        inventoryEntity.setQuantity(inventoryEntity.getQuantity() - quantity);
        inventoryRepository.save(inventoryEntity);
    }
}
