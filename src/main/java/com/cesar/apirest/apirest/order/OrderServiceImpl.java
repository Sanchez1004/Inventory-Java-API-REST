package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.inventory.InventoryService;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import com.cesar.apirest.apirest.utils.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (!checkItemsAvailability(orderRequest, orderRequest.getItemList())) {
            throw new OrderException("An Error occurred with the item list while creating the order");
        }

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

        if (!checkItemsAvailability(orderToUpdate, orderToUpdate.getItemList())) {
            throw new OrderException("An Error occurred with the item list while creating the order");
        }

        orderToUpdate.addItemList(newItemsList);
        return orderRepository.save(orderToUpdate);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository
                .findAll();
    }

    private boolean checkItemsAvailability(OrderEntity orderRequest, Map<String, Integer> itemList) {
        if (itemList.isEmpty()) {
            throw new OrderException("The order does not contain any items.");
        }

        double total = orderRequest.getTotal();

        for (Map.Entry<String, Integer> entry : itemList.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            ItemEntity item = itemService.itemExistsByName(itemName);
            inventoryService.isItemQuantityAvailable(item, quantity);
            inventoryService.deductItem(item, quantity);
            total += item.getPrice() * quantity;
        }

        orderRequest.setTotal(total);
        return false;
    }
}
