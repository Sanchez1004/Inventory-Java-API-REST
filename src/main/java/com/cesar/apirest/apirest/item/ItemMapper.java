package com.cesar.apirest.apirest.item;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemRequest toItemDTO(ItemEntity itemEntity) {
        return ItemRequest
                .builder()
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .build();
    }

    public ItemEntity toItemEntity(ItemRequest itemRequest) {
        return ItemEntity
                .builder()
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .build();
    }
}
