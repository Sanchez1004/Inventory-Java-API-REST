package com.cesar.apirest.apirest.item.dto;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between ItemRequest DTOs and ItemEntity entities.
 */
@Component
public class ItemMapper {

    /**
     * Converts an ItemEntity entity to an ItemRequest DTO.
     *
     * @param itemEntity the ItemEntity entity to convert
     * @return the corresponding ItemRequest DTO
     */
    public ItemRequest toItemDTO(ItemEntity itemEntity) {
        return ItemRequest
                .builder()
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .build();
    }

    /**
     * Converts an ItemRequest DTO to an ItemEntity entity.
     *
     * @param itemRequest the ItemRequest DTO to convert
     * @return the corresponding ItemEntity entity
     */
    public ItemEntity toItemEntity(ItemRequest itemRequest) {
        return ItemEntity
                .builder()
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .build();
    }
}
