package com.cesar.apirest.apirest.order.dto;

import com.cesar.apirest.apirest.order.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(OrderEntity orderEntity) {
        return OrderDTO
                .builder()
                .clientName(orderEntity.getClientName())
                .itemList(orderEntity.getItemList())
                .orderStatus(orderEntity.getOrderStatus())
                .total(orderEntity.getTotal())
                .build();
    }

    public OrderEntity toOrderEntity(OrderDTO orderDTO) {
        return OrderEntity
                .builder()
                .clientName(orderDTO.getClientName())
                .itemList(orderDTO.getItemList())
                .orderStatus(orderDTO.getOrderStatus())
                .total(orderDTO.getTotal())
                .build();
    }
}
