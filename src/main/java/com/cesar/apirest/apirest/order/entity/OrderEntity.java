package com.cesar.apirest.apirest.order.entity;

import com.cesar.apirest.apirest.utils.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private String id;
    private String clientName;

    @JsonProperty("itemList")
    private Map<String, Integer> itemList;
    private OrderStatus orderStatus;
    private double total;

    public void addItemList(Map<String, Integer> newItemsList) {
        itemList.putAll(newItemsList);
    }
}
