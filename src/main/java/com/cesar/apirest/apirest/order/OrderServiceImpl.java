package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity getOrderById(String id) {
        Optional<OrderEntity> orderOptional = orderRepository.getOrderById(id);
        return orderOptional.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        if(orderEntity.isListOfItemsEmpty()) {
            throw new OrderException("The order must hava at least one item");
        }

        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity addItemsToOrderById(Map<String, Integer> newItemList, String id) {
        OrderEntity orderEntity = getOrderById(id);
        OrderStatus orderStatus = orderEntity.getOrderStatus();
        String status = orderStatus.toString();
        if(status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("preparing")) {
            orderEntity.setItemMap(newItemList);
            return orderRepository.save(orderEntity);
        }
        throw new OrderException("No order found with id " + id);
    }
}
