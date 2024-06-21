package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.inventory.InventoryService;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import com.cesar.apirest.apirest.order.dto.OrderRequest;
import com.cesar.apirest.apirest.order.dto.OrderMapper;
import com.cesar.apirest.apirest.utils.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ItemService itemService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
    }

    @Override
    public OrderRequest getOrderById(String id) {
        Optional<OrderEntity> entityOrder = orderRepository.getOrderById(id);
        Optional<OrderRequest> orderDTO = entityOrder.map(orderMapper::toOrderDTO);
        return orderDTO.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    private OrderEntity getEntityOrderById(String id) {
        Optional<OrderEntity> order = orderRepository.getOrderById(id);
        return order.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    @Override
    public List<OrderRequest> getAllOrders() {
        List<OrderEntity> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(orderMapper::toOrderDTO)
                .toList();
    }

    @Override
    @Transactional
    public OrderRequest createOrder(OrderRequest orderRequest) {
        OrderEntity newOrder = orderMapper.toOrderEntity(orderRequest);
        checkItemsAvailability(newOrder, newOrder.getItemList());

        OrderEntity orderToCreate = OrderEntity
                .builder()
                .clientName(newOrder.getClientName())
                .itemList(newOrder.getItemList())
                .orderStatus(OrderStatus.PREPARING)
                .total(newOrder.getTotal())
                .build();

        orderRepository.save(orderToCreate);
        return orderMapper.toOrderDTO(orderToCreate);
    }

    @Override
    public List<OrderRequest> searchOrdersByClientName(String clientName) {
        if (clientName == null) {
            throw new OrderException("The client name cannot be empty");
        }

        List<OrderEntity> listOfOrders = orderRepository.findByClientName(clientName);
        return listOfOrders.stream().map(orderMapper::toOrderDTO).toList();
    }


    @Override
    @Transactional
    public OrderRequest addItemsToOrderById(Map<String, Integer> newItemsList, String id) {
        OrderEntity orderToUpdate = getEntityOrderById(id);
        OrderStatus orderStatus = orderToUpdate.getOrderStatus();
        String status = orderStatus.toString();
        if  (!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("preparing")) {
            throw new OrderException("Invalid order status, the order it's not available to be modified");
        }

        checkItemsAvailability(orderToUpdate, orderToUpdate.getItemList());

        orderToUpdate.addItemList(newItemsList);
        orderRepository.save(orderToUpdate);
        return orderMapper.toOrderDTO(orderToUpdate);
    }

    private void checkItemsAvailability(OrderEntity orderRequest, Map<String, Integer> itemList) {
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
    }
}
