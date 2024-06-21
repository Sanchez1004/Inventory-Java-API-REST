package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.order.dto.OrderRequest;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderRequest getOrderById(String id);
    List<OrderRequest> getAllOrders();
    OrderRequest createOrder(OrderRequest orderRequest);
    List<OrderRequest> searchOrdersByClientName(String clientName);
    OrderRequest addItemsToOrderById(Map<String, Integer> newItemList, String id);
}
