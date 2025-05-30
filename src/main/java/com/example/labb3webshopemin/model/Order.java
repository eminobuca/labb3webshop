package com.example.labb3webshopemin.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders") // Tabellnamn i databasen
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // Primärnyckel för ordern

    private String username;    // Kundens användarnamn som la ordern
    private boolean shipped;    // Om ordern är expedierad eller ej

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items; // Lista med produkter i ordern

    @Transient
    private double totalPrice;  // Beräknat fält - sparas ej i DB

    public Order() {}

    // Konstruktor för att skapa order med användarnamn och orderrader
    public Order(String username, List<OrderItem> items) {
        this.username = username;
        this.items = items;
        this.shipped = false;   // Standard är ej expedierad
    }

    // Getters och setters för fälten
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isShipped() { return shipped; }
    public void setShipped(boolean shipped) { this.shipped = shipped; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    // Beräknar totalpriset för alla produkter i ordern
    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Formaterar totalpriset till en sträng med två decimaler och "kr"
    public String getFormattedTotal() {
        return String.format("%.2f kr", getTotal());
    }

    // Getter och setter för totalPrice (används ej i databas)
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}