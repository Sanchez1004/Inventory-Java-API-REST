package com.cesar.apirest.apirest.inventory.dto;

import com.cesar.apirest.apirest.inventory.entity.InventoryEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between InventoryRequest DTOs and InventoryEntity entities.
 */
@Component
public class InventoryMapper {

    /**
     * Converts an InventoryRequest DTO to an InventoryEntity entity.
     *
     * @param inventoryRequest the InventoryRequest DTO to convert
     * @return the corresponding InventoryEntity entity
     */
    public InventoryEntity toEntity(InventoryRequest inventoryRequest) {
        return InventoryEntity
                .builder()
                .item(inventoryRequest.getItem())
                .quantity(inventoryRequest.getQuantity())
                .build();
    }

    /**
     * Converts an InventoryEntity entity to an InventoryRequest DTO.
     *
     * @param inventoryEntity the InventoryEntity entity to convert
     * @return the corresponding InventoryRequest DTO
     */
    public InventoryRequest toDTO(InventoryEntity inventoryEntity) {
        return InventoryRequest
                .builder()
                .item(inventoryEntity.getItem())
                .quantity(inventoryEntity.getQuantity())
                .build();
    }
}
