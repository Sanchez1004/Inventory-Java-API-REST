package com.cesar.apirest.apirest.order.dto;

import com.cesar.apirest.apirest.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between OrderRequest DTOs and OrderEntity entities.
 */
@Component
public class OrderMapper {

    /**
     * Converts an OrderEntity entity to an OrderRequest DTO.
     *
     * @param orderEntity the OrderEntity entity to convert
     * @return the corresponding OrderRequest DTO
     */
    public OrderRequest toOrderDTO(OrderEntity orderEntity) {
        return OrderRequest
                .builder()
                .clientName(orderEntity.getClientName())
                .itemList(orderEntity.getItemList())
                .orderStatus(orderEntity.getOrderStatus())
                .total(orderEntity.getTotal())
                .build();
    }

    /**
     * Converts an OrderRequest DTO to an OrderEntity entity.
     *
     * @param orderRequest the OrderRequest DTO to convert
     * @return the corresponding OrderEntity entity
     */
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
