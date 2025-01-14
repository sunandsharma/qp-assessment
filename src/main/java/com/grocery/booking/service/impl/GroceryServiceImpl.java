package com.grocery.booking.service.impl;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;
import com.grocery.booking.repository.GroceryItemRepository;
import com.grocery.booking.repository.OrderRepository;
import com.grocery.booking.service.GroceryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroceryServiceImpl implements GroceryService {
    private final GroceryItemRepository groceryItemRepository;
    private final OrderRepository orderRepository;

    public GroceryServiceImpl(GroceryItemRepository groceryItemRepository, OrderRepository orderRepository) {
        this.groceryItemRepository = groceryItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<GroceryItem> findAllItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public GroceryItem addItem(GroceryItem item) {
        return groceryItemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        groceryItemRepository.deleteById(id);
    }

    @Override
    public Order placeOrder(String userName, List<Long> itemIds) {
        List<GroceryItem> items = groceryItemRepository.findAllById(itemIds);
        double totalPrice = items.stream().mapToDouble(GroceryItem::getPrice).sum();

        Order order = new Order();
        order.setUserName(userName);
        order.setGroceryItemIdsFromList(itemIds);
        order.setTotalPrice(totalPrice);

        for (GroceryItem groceryItem : items) {
            if (groceryItem.getInventory() !=0)
            groceryItem.setInventory(groceryItem.getInventory()-1);
            else
                throw new IllegalArgumentException("Stock empty for item : " +groceryItem.getId());
        }

        groceryItemRepository.saveAll(items);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getUserOrders(String userName) {
        List<Order> orders = orderRepository.findAll();
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserName().equals(userName)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public GroceryItem updateItemDetails(Long id, GroceryItem updatedItem) {
        return groceryItemRepository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setPrice(updatedItem.getPrice());
            return groceryItemRepository.save(item);
        }).orElseThrow(() -> new IllegalArgumentException("Grocery item not found with id: " + id));
    }

    @Override
    public GroceryItem updateInventory(Long id, Integer newInventory) {
        return groceryItemRepository.findById(id).map(item -> {
            item.setInventory(newInventory);
            return groceryItemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Grocery item not found with id: " + id));
    }
}