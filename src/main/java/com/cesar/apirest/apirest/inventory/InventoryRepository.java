package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {
    @Query("{'itemEntity': ?0}")
    InventoryEntity findByItemEntity(ItemEntity item);
}
