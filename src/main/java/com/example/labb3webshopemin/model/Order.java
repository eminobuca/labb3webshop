package com.example.labb3webshopemin.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private boolean shipped;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;


    @Transient
    private double totalPrice;

    public Order() {}

    public Order(String username, List<OrderItem> items) {
        this.username = username;
        this.items = items;
        this.shipped = false;
    }

    // Getters och setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isShipped() { return shipped; }
    public void setShipped(boolean shipped) { this.shipped = shipped; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
    public String getFormattedTotal() {
        return String.format("%.2f kr", getTotal());
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
