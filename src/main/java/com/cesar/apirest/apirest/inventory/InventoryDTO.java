package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryDTO {
    ItemEntity item;
    int quantity;
}
