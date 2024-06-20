package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.order.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO getOrderById(String id);
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder(OrderDTO orderDTO);
    List<OrderDTO> searchOrdersByClientName(String clientName);
    OrderDTO addItemsToOrderById(Map<String, Integer> newItemList, String id);
}
