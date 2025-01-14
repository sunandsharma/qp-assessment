package com.grocery.booking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String groceryItemIds;

    @Column(nullable = false)
    private Double totalPrice;

    public List<Long> getGroceryItemIdsAsList() {
        if (groceryItemIds == null || groceryItemIds.isEmpty()) {
            return new ArrayList<>();
        }
        String[] ids = groceryItemIds.split(",");
        List<Long> idList = new ArrayList<>();
        for (String id : ids) {
            idList.add(Long.parseLong(id.trim()));
        }
        return idList;
    }

    // Utility method to set item IDs from a list
    public void setGroceryItemIdsFromList(List<Long> ids) {
        this.groceryItemIds = ids.stream()
                .map(String::valueOf)
                .reduce((id1, id2) -> id1 + "," + id2)
                .orElse("");
    }
}
