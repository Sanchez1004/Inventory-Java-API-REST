package com.cesar.apirest.apirest.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryEntity, String> { }