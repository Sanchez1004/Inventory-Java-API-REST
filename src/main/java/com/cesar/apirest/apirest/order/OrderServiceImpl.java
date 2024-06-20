package com.cesar.apirest.apirest.order;

import com.cesar.apirest.apirest.exception.OrderException;
import com.cesar.apirest.apirest.inventory.InventoryService;
import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.item.service.ItemService;
import com.cesar.apirest.apirest.order.dto.OrderDTO;
import com.cesar.apirest.apirest.order.dto.OrderMapper;
import com.cesar.apirest.apirest.utils.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public OrderDTO getOrderById(String id) {
        Optional<OrderEntity> entityOrder = orderRepository.getOrderById(id);
        Optional<OrderDTO> orderDTO = entityOrder.map(orderMapper::toOrderDTO);
        return orderDTO.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    private OrderEntity getEntityOrderById(String id) {
        Optional<OrderEntity> order = orderRepository.getOrderById(id);
        return order.orElseThrow(() -> new OrderException("Order not found with id " + id));
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity newOrder = orderMapper.toOrderEntity(orderDTO);
        if (!checkItemsAvailability(newOrder, newOrder.getItemList())) {
            throw new OrderException("An Error occurred with the item list while creating the order");
        }

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
    public List<OrderDTO> searchOrdersByClientName(String clientName) {
        if (clientName == null) {
            throw new OrderException("The client name cannot be empty");
        }

        List<OrderEntity> listOfOrders = orderRepository.findByClientName(clientName);
        return listOfOrders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public OrderDTO addItemsToOrderById(Map<String, Integer> newItemsList, String id) {
        OrderEntity orderToUpdate = getEntityOrderById(id);
        OrderStatus orderStatus = orderToUpdate.getOrderStatus();
        String status = orderStatus.toString();
        if  (!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("preparing")) {
            throw new OrderException("Invalid order status, the order it's not available to be modified");
        }

        if (!checkItemsAvailability(orderToUpdate, orderToUpdate.getItemList())) {
            throw new OrderException("An Error occurred with the item list while creating the order");
        }

        orderToUpdate.addItemList(newItemsList);
        orderRepository.save(orderToUpdate);
        return orderMapper.toOrderDTO(orderToUpdate);
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
