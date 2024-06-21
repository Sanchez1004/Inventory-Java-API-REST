package com.cesar.apirest.apirest.inventory.dto;

import com.cesar.apirest.apirest.inventory.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryEntity toEntity(InventoryRequest inventoryRequest) {
        return InventoryEntity
                .builder()
                .item(inventoryRequest.getItem())
                .quantity(inventoryRequest.getQuantity())
                .build();
    }

    public InventoryRequest toDTO(InventoryEntity inventoryEntity) {
        return InventoryRequest
                .builder()
                .item(inventoryEntity.getItem())
                .quantity(inventoryEntity.getQuantity())
                .build();
    }
}
