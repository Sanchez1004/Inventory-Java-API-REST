package com.cesar.apirest.apirest.order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderEntity getOrderById(String id);
    OrderEntity createOrder(OrderEntity orderEntity);
    OrderEntity addItemsToOrderById(Map<String, Integer> newItemList, String id);
    List<OrderEntity> getAllOrders();
}
