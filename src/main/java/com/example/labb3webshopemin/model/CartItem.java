package com.example.labb3webshopemin.model;

// Representerar en vara i kundens varukorg med produkt och antal
public class CartItem {
    private Product product;   // Produkten i varukorgen
    private int quantity;      // Hur många av produkten kunden vill ha

    // Konstruktor som skapar en CartItem med antal satt till 1
    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    // Returnerar produkten
    public Product getProduct() {
        return product;
    }

    // Returnerar antal av produkten i varukorgen
    public int getQuantity() {
        return quantity;
    }

    // Ökar antal med 1, t.ex. vid "lägg till fler" knapp
    public void incrementQuantity() {
        this.quantity++;
    }

    // Sätter ett specifikt antal för produkten
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
