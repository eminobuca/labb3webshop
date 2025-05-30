package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.CartItem;
import com.example.labb3webshopemin.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    // Här sparar vi alla varor som lagts till i kundens varukorg
    private final List<CartItem> cartItems = new ArrayList<>();

    // Lägg till en produkt i varukorgen
    // Om produkten redan finns ökar vi bara antalet
    public void addToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.incrementQuantity();  // Öka antal för produkten i varukorgen
                return;
            }
        }
        // Om produkten inte finns i varukorgen, lägg till den som ny rad
        cartItems.add(new CartItem(product));
    }

    // Hämta alla produkter som finns i varukorgen
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Ändra antal på en produkt i varukorgen baserat på produktens id
    public void updateQuantity(Long productId, int quantity) {
        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity)); // Sätt nytt antal
    }

    // Ta bort en produkt från varukorgen baserat på produktens id
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    // Beräkna totalpriset för alla varor i varukorgen (pris * antal)
    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Töm varukorgen helt
    public void clearCart() {
        cartItems.clear();
    }
}
