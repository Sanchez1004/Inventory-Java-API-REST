package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ORDER")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemEntity> itemOrderList;
}