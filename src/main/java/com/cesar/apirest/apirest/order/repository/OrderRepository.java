package com.cesar.apirest.apirest.order.repository;

import com.cesar.apirest.apirest.order.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    Optional<OrderEntity> getOrderById(String id);
    List<OrderEntity> findByClientName(String clientName);
}
