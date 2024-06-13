package com.cesar.apirest.apirest.item.entity;

import com.cesar.apirest.apirest.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "ITEM")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    @ManyToMany
    private List<OrderEntity> orders;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
