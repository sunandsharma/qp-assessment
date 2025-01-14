package com.grocery.booking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "grocery_items")
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer inventory;
}
