package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.utils.OrderStatus;
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
    private Map<String, Integer> itemList;
    private OrderStatus orderStatus;
    private double total;
}
