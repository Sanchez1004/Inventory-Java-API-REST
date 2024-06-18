package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "inventory")
public class InventoryEntity {
    @Id
    private String id;
    private ItemEntity item;
    private int quantity;

    public String getItemName() {
        return item.getName();
    }
}
