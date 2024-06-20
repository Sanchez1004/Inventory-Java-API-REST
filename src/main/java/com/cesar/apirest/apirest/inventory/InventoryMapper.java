package com.cesar.apirest.apirest.inventory;

import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryEntity toEntity(InventoryDTO inventoryDTO) {
        return InventoryEntity
                .builder()
                .item(inventoryDTO.getItem())
                .quantity(inventoryDTO.getQuantity())
                .build();
    }

    public InventoryDTO toDTO(InventoryEntity inventoryEntity) {
        return InventoryDTO
                .builder()
                .item(inventoryEntity.getItem())
                .quantity(inventoryEntity.getQuantity())
                .build();
    }
}
