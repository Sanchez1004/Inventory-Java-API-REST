package com.cesar.apirest.apirest.order.dto;

import com.cesar.apirest.apirest.order.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderRequest toOrderDTO(OrderEntity orderEntity) {
        return OrderRequest
                .builder()
                .clientName(orderEntity.getClientName())
                .itemList(orderEntity.getItemList())
                .orderStatus(orderEntity.getOrderStatus())
                .total(orderEntity.getTotal())
                .build();
    }

    public OrderEntity toOrderEntity(OrderRequest orderRequest) {
        return OrderEntity
                .builder()
                .clientName(orderRequest.getClientName())
                .itemList(orderRequest.getItemList())
                .orderStatus(orderRequest.getOrderStatus())
                .total(orderRequest.getTotal())
                .build();
    }
}
