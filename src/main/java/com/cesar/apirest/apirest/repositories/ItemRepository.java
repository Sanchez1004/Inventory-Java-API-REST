package com.cesar.apirest.apirest.repositories;


import com.cesar.apirest.apirest.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
