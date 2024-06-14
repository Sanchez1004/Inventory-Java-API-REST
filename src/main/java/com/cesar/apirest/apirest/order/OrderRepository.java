package com.cesar.apirest.apirest.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    Optional<OrderEntity> getOrderById(String id);
}
