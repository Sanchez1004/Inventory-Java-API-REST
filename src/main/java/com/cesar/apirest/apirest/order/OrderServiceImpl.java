package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.inventory.InventoryService;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import com.cesar.apirest.apirest.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    public OrderServiceImpl(OrderRepository orderRepository, ItemService itemService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
    }

    @Override
    public OrderEntity getOrderById(String id) {
        Optional<OrderEntity> orderOptional = orderRepository.getOrderById(id);
        return orderOptional.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : order.getItemList().entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            ItemEntity itemEntity = itemService.itemExistsByName(itemName);

            if (!inventoryService.isItemAvailable(itemEntity, quantity)) {
                throw new OrderException("Insufficient inventory for item: " + itemName);
            }

            inventoryService.deductItem(itemEntity, quantity);

            total += itemEntity.getPrice() * quantity;
        }

        OrderEntity orderEntity = OrderEntity.builder()
                .clientName(order.getClientName())
                .itemList(order.getItemList())
                .orderStatus(OrderStatus.PREPARING)
                .total(total)
                .build();

        return orderRepository.save(orderEntity);
    }


    @Override
    public OrderEntity addItemsToOrderById(Map<String, Integer> newItemList, String id) {
        OrderEntity orderEntity = getOrderById(id);
        OrderStatus orderStatus = orderEntity.getOrderStatus();
        String status = orderStatus.toString();
        if (status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("preparing")) {
            orderEntity.setItemList(newItemList);
            return orderRepository.save(orderEntity);
        }
        throw new OrderException("No order found with id " + id);
    }
}
