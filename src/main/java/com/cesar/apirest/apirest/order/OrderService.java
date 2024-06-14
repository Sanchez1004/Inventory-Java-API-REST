package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.item.entity.ItemEntity;

import java.util.Map;

public interface OrderService {
    OrderEntity getOrderById(String id);
    OrderEntity createOrder(OrderEntity orderEntity);
    OrderEntity addItemsToOrderById(Map<String, Integer> newItemList, String id);
}
