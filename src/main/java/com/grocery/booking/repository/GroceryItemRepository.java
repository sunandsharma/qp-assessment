package com.grocery.booking.repository;

import com.grocery.booking.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {}
