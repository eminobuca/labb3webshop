package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.CartItem;
import com.example.labb3webshopemin.model.Order;
import com.example.labb3webshopemin.model.OrderItem;
import com.example.labb3webshopemin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(String username, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUsername(username);
        order.setShipped(false);

        List<OrderItem> items = cartItems.stream()
                .map(cartItem -> new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toList());

        order.setItems(items);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getUnshippedOrders() {
        return orderRepository.findByShippedFalse();
    }

    public List<Order> getShippedOrders() {
        return orderRepository.findByShippedTrue();
    }

    public void markAsShipped(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setShipped(true);
        orderRepository.save(order);
    }
}