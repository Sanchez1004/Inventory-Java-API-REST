package com.cesar.apirest.apirest.inventory;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Repository interface for managing InventoryEntity entities.
 */
public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {

    /**
     * Retrieves an inventory entity by its associated item entity.
     *
     * @param item the item entity to search for in the inventory
     * @return the inventory entity associated with the specified item
     */
    @Query("{'itemEntity': ?0}")
    InventoryEntity findByItemEntity(ItemEntity item);

    /**
     * Retrieves a list of inventory entities whose item names match the specified regex pattern.
     *
     * @param regex the regex pattern to match against item names
     * @return a list of inventory entities whose item names match the regex pattern
     */
    List<InventoryEntity> findByItemNameRegex(String regex);
}
