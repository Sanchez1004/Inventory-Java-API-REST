package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Document(collection = "INVENTORY")
public class InventoryEntity {
    @Id
    private String id;
    private Map<ItemEntity, Integer> itemsInventory;
}
