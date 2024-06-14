package com.cesar.apirest.apirest.item.repository;


import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    ItemEntity getItemByName(String name);

    @Query("SELECT i.name FROM ITEM i")
    List<String> findAllItemNames();

    ItemEntity findByName(String name);

    boolean existsById(Long id);
}
