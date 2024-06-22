package com.cesar.apirest.apirest.item.repository;


import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Repository interface for managing ItemEntity entities.
 */
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    /**
     * Retrieves a list of names of all items.
     *
     * @return a list containing the names of all items
     */
    @Query("SELECT i.name FROM ITEM i")
    List<String> getALlItemNames();

    /**
     * Retrieves an item entity by its name.
     *
     * @param name the name of the item to retrieve
     * @return the item entity with the specified name
     */
    ItemEntity findByName(String name);

    /**
     * Checks if an item entity exists by its ID.
     *
     * @param id the ID of the item entity to check
     * @return true if an item entity with the specified ID exists, false otherwise
     */
    boolean existsById(@NonNull Long id);
}
