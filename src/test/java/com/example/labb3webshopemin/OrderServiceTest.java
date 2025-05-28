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
        // Skapa ny testorder
        Order order = new Order();
        order.setUsername("testuser");
        order.setShipped(false);

        // Spara i testdatabasen
        orderRepository.save(order);

        // Markera som expedierad
        orderService.markAsShipped(order.getId());

        // Hämta uppdaterad order
        Order updatedOrder = orderRepository.findById(order.getId()).orElseThrow();

        // Kontrollera att den är expedierad
        Assertions.assertTrue(updatedOrder.isShipped());
    }

    @Test
    public void testGetAllOrders() {
        // Skapa och spara en testorder
        Order order = new Order();
        order.setUsername("testuser2");
        order.setShipped(false);
        orderRepository.save(order);

        // Hämta alla ordrar
        List<Order> orders = orderService.getAllOrders();

        // Kontrollera att det finns minst en order
        Assertions.assertFalse(orders.isEmpty());
    }
}