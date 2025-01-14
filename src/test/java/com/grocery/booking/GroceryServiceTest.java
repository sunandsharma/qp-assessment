package com.grocery.booking;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;
import com.grocery.booking.repository.GroceryItemRepository;
import com.grocery.booking.repository.OrderRepository;
import com.grocery.booking.service.impl.GroceryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroceryServiceTest {

    @Mock
    private GroceryItemRepository groceryItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private GroceryServiceImpl groceryServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllItems() {
        // Arrange
        GroceryItem item1 = new GroceryItem();
        item1.setId(1L);
        item1.setName("Apple");
        item1.setPrice(1.5);
        item1.setInventory(100);

        GroceryItem item2 = new GroceryItem();
        item2.setId(2L);
        item2.setName("Banana");
        item2.setPrice(0.5);
        item2.setInventory(200);

        when(groceryItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        // Act
        List<GroceryItem> items = groceryServiceImpl.findAllItems();

        // Assert
        assertEquals(2, items.size());
        assertEquals("Apple", items.get(0).getName());
        verify(groceryItemRepository, times(1)).findAll();
    }

    @Test
    void testAddItem() {
        // Arrange
        GroceryItem item = new GroceryItem();
        item.setName("Apple");
        item.setPrice(1.5);
        item.setInventory(100);

        when(groceryItemRepository.save(item)).thenReturn(item);

        // Act
        GroceryItem savedItem = groceryServiceImpl.addItem(item);

        // Assert
        assertNotNull(savedItem);
        assertEquals("Apple", savedItem.getName());
        verify(groceryItemRepository, times(1)).save(item);
    }

    @Test
    void testDeleteItem() {
        // Arrange
        Long itemId = 1L;
        doNothing().when(groceryItemRepository).deleteById(itemId);

        // Act
        groceryServiceImpl.deleteItem(itemId);

        // Assert
        verify(groceryItemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void testPlaceOrder() {
        // Arrange
        GroceryItem item1 = new GroceryItem();
        item1.setId(1L);
        item1.setName("Apple");
        item1.setPrice(1.5);

        GroceryItem item2 = new GroceryItem();
        item2.setId(2L);
        item2.setName("Banana");
        item2.setPrice(0.5);

        List<Long> itemIds = Arrays.asList(1L, 2L);
        when(groceryItemRepository.findAllById(itemIds)).thenReturn(Arrays.asList(item1, item2));

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setUserName("testuser");
        savedOrder.setGroceryItemIdsFromList(itemIds);
        savedOrder.setTotalPrice(2.0);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order order = groceryServiceImpl.placeOrder("testuser", itemIds);

        // Assert
        assertNotNull(order);
        assertEquals("testuser", order.getUserName());
        assertEquals(2, order.getGroceryItemIdsAsList().size());
        assertEquals(2.0, order.getTotalPrice());
        verify(groceryItemRepository, times(1)).findAllById(itemIds);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetUserOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setId(1L);
        order1.setUserName("testuser");

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUserName("testuser");

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> orders = groceryServiceImpl.getUserOrders("testuser");

        // Assert
        assertEquals(2, orders.size());
        assertEquals("testuser", orders.get(0).getUserName());
        verify(orderRepository, times(1)).findAll();
    }
}
