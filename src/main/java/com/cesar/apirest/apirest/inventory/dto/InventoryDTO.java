package com.cesar.apirest.apirest.inventory.dto;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private ItemEntity item;
    private int quantity;
}
