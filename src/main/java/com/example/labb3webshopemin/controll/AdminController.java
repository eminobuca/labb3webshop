package com.example.labb3webshopemin.controll;

import com.example.labb3webshopemin.model.Order;
import com.example.labb3webshopemin.model.OrderItem;
import com.example.labb3webshopemin.model.Product;
import com.example.labb3webshopemin.service.OrderService;
import com.example.labb3webshopemin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    // Visa oexpedierade ordrar
    @GetMapping("/orders")
    public String viewUnshippedOrders(Model model) {
        List<Order> unshippedOrders = orderService.getUnshippedOrders();
        for (Order order : unshippedOrders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }
        model.addAttribute("orders", unshippedOrders);
        return "admin-orders";
    }

    // Visa expedierade ordrar
    @GetMapping("/orders/shipped")
    public String viewShippedOrders(Model model) {
        List<Order> shippedOrders = orderService.getShippedOrders();
        for (Order order : shippedOrders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }
        model.addAttribute("orders", shippedOrders);
        return "admin-shipped-orders";
    }

    // Markera som expedierad
    @PostMapping("/orders/{id}/ship")
    public String markOrderAsShipped(@PathVariable Long id) {
        orderService.markAsShipped(id);
        return "redirect:/admin/orders";
    }

    // Visa och hantera produkter
    @GetMapping("/products")
    public String showAdminProductPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("newProduct", new Product());
        return "admin-products";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }
}