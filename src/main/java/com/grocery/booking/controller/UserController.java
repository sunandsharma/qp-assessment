package com.grocery.booking.controller;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;
import com.grocery.booking.service.impl.GroceryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final GroceryServiceImpl groceryServiceImpl;

    public UserController(GroceryServiceImpl groceryServiceImpl) {
        this.groceryServiceImpl = groceryServiceImpl;
    }

    @GetMapping("/items")
    public List<GroceryItem> viewItems() {
        return groceryServiceImpl.findAllItems();
    }

    @PostMapping("/orders")
    public Order placeOrder(@RequestParam String userName, @RequestBody List<Long> itemIds) {
        return groceryServiceImpl.placeOrder(userName, itemIds);
    }

    @GetMapping("/orders")
    public List<Order> getUserOrders(@RequestParam String userName) {
        return groceryServiceImpl.getUserOrders(userName);
    }
}
