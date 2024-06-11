package com.cesar.apirest.apirest.item.repository;


import com.cesar.apirest.apirest.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> { }
