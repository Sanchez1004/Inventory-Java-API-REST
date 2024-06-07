package com.cesar.apirest.apirest.repository;


import com.cesar.apirest.apirest.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> { }
