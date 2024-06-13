package com.cesar.apirest.apirest.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends MongoRepository<OrderRepository, Long> {

}
