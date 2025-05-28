package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.CartItem;
import com.example.labb3webshopemin.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void updateQuantity(Long productId, int quantity) {
        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}
