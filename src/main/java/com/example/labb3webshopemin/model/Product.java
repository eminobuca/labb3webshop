package com.example.labb3webshopemin.model;

import jakarta.persistence.*;

@Entity
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;            // Primärnyckel för produkten

        private String name;        // Produktnamn
        private String description; // Beskrivning av produkten
        private double price;       // Pris per styck
        private int stock;          // Lagerantal

        // Tom konstruktor krävs för JPA
        public Product() {}

        // Konstruktor för enkel skapelse av produktobjekt
        public Product(String name, String description, double price, int stock) {
                this.name = name;
                this.description = description;
                this.price = price;
                this.stock = stock;
        }

        // Getters och setters för fälten
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }

        public double getPrice() { return price; }

        public int getStock() { return stock; }
}
