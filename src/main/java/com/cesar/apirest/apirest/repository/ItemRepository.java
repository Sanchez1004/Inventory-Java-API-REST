package com.cesar.apirest.apirest.repository;


import com.cesar.apirest.apirest.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {}
