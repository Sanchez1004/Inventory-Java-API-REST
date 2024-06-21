package com.cesar.apirest.apirest.order.dto;


import com.cesar.apirest.apirest.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String clientName;
    private Map<String, Integer> itemList;
    private OrderStatus orderStatus;
    private double total;
}
