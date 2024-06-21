package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.inventory.dto.InventoryRequest;
import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.inventory.dto.InventoryMapper;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ItemService itemService;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper, ItemService itemService) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.itemService = itemService;
    }

    @Override
    public InventoryRequest getInventoryItemById(String id) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findById(id);
        Optional<InventoryRequest> inventoryDTO = inventoryEntity.map(inventoryMapper::toDTO);
        return inventoryDTO.orElseThrow(() -> new InventoryException("Item not found with id: " + id));
    }

    @Override
    public InventoryRequest createItemInInventory(InventoryRequest inventoryRequest) {
        if (inventoryRequest.getItem() == null) {
            throw new InventoryException("The item cannot be empty");
        }

        if (inventoryRequest.getQuantity() <= 0) {
            throw new InventoryException("The quantity cannot be 0 o negative");
        }

        itemService.createItem(inventoryRequest.getItem());

        InventoryEntity updatedInventory = inventoryMapper.toEntity(inventoryRequest);

        inventoryRepository.save(updatedInventory);
        return inventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public InventoryRequest addStockToItemById(String id, int quantity) {
        InventoryRequest inventoryRequest = getInventoryItemById(id);
        if (quantity <= 0) {
            throw new InventoryException("Quantity cannot be negative o zero");
        }

        inventoryRequest.setQuantity(quantity);
        inventoryRepository.save(inventoryMapper.toEntity(inventoryRequest));
        return inventoryRequest;
    }

    @Override
    public List<InventoryRequest> getInventoryByItemNameContaining(String keyword) throws InventoryException {
        String regex = ".*" + keyword + ".*";
        List<InventoryEntity> inventoryEntityList = inventoryRepository.findByItemNameRegex(regex);
        return inventoryEntityList.stream()
                .map(inventoryMapper::toDTO)
                .collect(Collectors.toList());
    }


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
