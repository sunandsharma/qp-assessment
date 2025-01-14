package com.grocery.booking.service;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;

import java.util.List;

public interface GroceryService {

    List<GroceryItem> findAllItems();

    GroceryItem addItem(GroceryItem item);

    void deleteItem(Long id);

    Order placeOrder(String userName, List<Long> itemIds);

    List<Order> getUserOrders(String userName);

    GroceryItem updateItemDetails(Long id, GroceryItem updatedItem);

    GroceryItem updateInventory(Long id, Integer newInventory);

}
