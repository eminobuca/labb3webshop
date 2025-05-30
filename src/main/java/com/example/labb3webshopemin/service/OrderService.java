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

    // När en kund lägger en order skapas ett Order-objekt med dess innehåll
    public Order placeOrder(String username, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUsername(username);
        order.setShipped(false);  // Ny order är ej expedierad från start

        // Skapa OrderItem för varje varukorgsrad och koppla till ordern
        List<OrderItem> items = cartItems.stream()
                .map(cartItem -> new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toList());

        order.setItems(items);

        // Spara ordern i databasen och returnera sparat objekt
        return orderRepository.save(order);
    }

    // Hämta alla ordrar (både expedierade och oexpedierade)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Hämta bara oexpedierade ordrar (shipped = false)
    public List<Order> getUnshippedOrders() {
        return orderRepository.findByShippedFalse();
    }

    // Hämta bara expedierade ordrar (shipped = true)
    public List<Order> getShippedOrders() {
        return orderRepository.findByShippedTrue();
    }

    // Markera en order som expedierad (ändra shipped till true)
    public void markAsShipped(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setShipped(true);
        orderRepository.save(order);
    }
}
