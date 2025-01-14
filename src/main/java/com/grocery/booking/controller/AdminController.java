package com.grocery.booking.controller;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.service.impl.GroceryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final GroceryServiceImpl groceryServiceImpl;

    public AdminController(GroceryServiceImpl groceryServiceImpl) {
        this.groceryServiceImpl = groceryServiceImpl;
    }

    @GetMapping("/items")
    public List<GroceryItem> getAllItems() {
        return groceryServiceImpl.findAllItems();
    }

    @PostMapping("/items")
    public GroceryItem addItem(@RequestBody GroceryItem item) {
        return groceryServiceImpl.addItem(item);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        groceryServiceImpl.deleteItem(id);
    }

    // Endpoint to update details (name and price) of an existing grocery item
    @PutMapping("/items/{id}")
    public GroceryItem updateItemDetails(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
        return groceryServiceImpl.updateItemDetails(id, updatedItem);
    }

    // Endpoint to manage inventory levels of an existing grocery item
    @PatchMapping("/items/{id}/inventory")
    public GroceryItem updateInventory(@PathVariable Long id, @RequestParam Integer newInventory) {
        return groceryServiceImpl.updateInventory(id, newInventory);
    }
}
