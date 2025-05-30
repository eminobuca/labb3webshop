package com.example.labb3webshopemin.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // Primärnyckel för orderraden

    @ManyToOne
    private Order order;        // Referens till ordern denna rad hör till

    @ManyToOne
    private Product product;    // Produkten som beställts

    private int quantity;       // Antal av produkten i denna orderrad

    public OrderItem() {}

    // Konstruktor som sätter order, produkt och antal
    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters och setter för produkt och antal
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
}
