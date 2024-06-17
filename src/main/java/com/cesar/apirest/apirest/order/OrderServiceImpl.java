package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.inventory.InventoryService;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import com.cesar.apirest.apirest.utils.OrderStatus;
import jakarta.transaction.Transactional;
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
    @Transactional
    public OrderEntity createOrder(OrderEntity orderRequest) {
        checkItemsOrderAvailability(orderRequest);

        OrderEntity order = OrderEntity
                .builder()
                .clientName(orderRequest.getClientName())
                .itemList(orderRequest.getItemList())
                .orderStatus(OrderStatus.PREPARING)
                .total(orderRequest.getTotal())
                .build();

        return orderRepository.save(order);
    }


    @Override
    @Transactional
    public OrderEntity addItemsToOrderById(Map<String, Integer> newItemsList, String id) {
        OrderEntity orderToUpdate = getOrderById(id);
        OrderStatus orderStatus = orderToUpdate.getOrderStatus();
        String status = orderStatus.toString();
        if  (!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("preparing")) {
            throw new OrderException("Invalid order status, the order it's not available to be modified");
        }

        checkItemsOrderAvailability(orderToUpdate);
        orderToUpdate.addItemList(newItemsList);
        return orderRepository.save(orderToUpdate);
    }

    @Override
    public void checkItemsOrderAvailability(OrderEntity orderRequest) {
        if (orderRequest.getItemList() == null || orderRequest.getItemList().isEmpty()) {
            throw new OrderException("The order does not contain any items.");
        }

        double total = 0.0;

        for (Map.Entry<String, Integer> entry : orderRequest.getItemList().entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            ItemEntity itemEntity = itemService.itemExistsByName(itemName);
            if (itemEntity == null) {
                throw new OrderException("Item not found: " + itemName);
            }

            if (!inventoryService.isItemAvailable(itemEntity, quantity)) {
                throw new OrderException("Insufficient inventory for item: " + itemName);
            }

            inventoryService.deductItem(itemEntity, quantity);
            total += itemEntity.getPrice() * quantity;
        }

        orderRequest.setTotal(total);
    }
}
