package com.example.labb3webshopemin.model;

import jakarta.persistence.*;

@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String description;
private double price;
private int stock;

// Tom konstruktor krävs för JPA
        public Product() {
}

// Extra konstruktor för testdata
        public Product(String name, String description, double price, int stock) {
this.name = name;
this.description = description;
this.price = price;
this.stock = stock;
}

// Getters & Setters
        public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getName() { return name; }
public void setName(String name) { this.name = name; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public double getPrice() { return price; }
public void setPrice(double price) { this.price = price; }

public int getStock() { return stock; }
public void setStock(int stock) { this.stock = stock; }
}