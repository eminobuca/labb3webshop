package com.example.labb3webshopemin;

import com.example.labb3webshopemin.model.Order;
import com.example.labb3webshopemin.repository.OrderRepository;
import com.example.labb3webshopemin.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testMarkAsShipped() {
        // Skapa en ny order med användarnamn och "inte expedierad"-status
        Order order = new Order();
        order.setUsername("testuser");
        order.setShipped(false);

        // Spara ordern i testdatabasen (för att kunna testa på riktigt)
        orderRepository.save(order);

        // Anropa funktionen för att markera ordern som expedierad
        orderService.markAsShipped(order.getId());

        // Hämta den uppdaterade ordern från databasen
        Order updatedOrder = orderRepository.findById(order.getId()).orElseThrow();

        // Kontrollera att ordern nu är markerad som expedierad (true)
        Assertions.assertTrue(updatedOrder.isShipped());
    }

    @Test
    public void testGetAllOrders() {
        // Skapa och spara en testorder för att säkerställa att databasen inte är tom
        Order order = new Order();
        order.setUsername("testuser2");
        order.setShipped(false);
        orderRepository.save(order);

        // Hämta alla ordrar från tjänsten
        List<Order> orders = orderService.getAllOrders();

        // Kontrollera att minst en order finns i listan (dvs att metoden fungerar)
        Assertions.assertFalse(orders.isEmpty());
    }
}
