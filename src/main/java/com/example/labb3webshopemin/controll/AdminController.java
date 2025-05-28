package com.example.labb3webshopemin.controll;
import com.example.labb3webshopemin.model.Order;
import com.example.labb3webshopemin.model.Product;
import com.example.labb3webshopemin.service.OrderService;
import com.example.labb3webshopemin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.labb3webshopemin.repository.OrderRepository;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    // Visa alla ordrar (kan ta bort om du inte vill visa alla)
    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    // Visa oexpedierade ordrar
    @GetMapping("/orders/unshipped")
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
        List<Order> shippedOrders = orderService.getAllOrders().stream()
                .filter(Order::isShipped)
                .toList();
        for (Order order : shippedOrders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }
        model.addAttribute("orders", shippedOrders);
        return "admin-shipped-orders";
    }

    // Markera en order som expedierad
    @PostMapping("/orders/{id}/ship")
    public String markAsShipped(@PathVariable Long id) {
        orderService.markAsShipped(id);
        return "redirect:/admin/orders/unshipped";  // Redirect till oexpedierade efter markering
    }

    // Visa produkthanteringssida
    @GetMapping("/products")
    public String showAdminProductPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("newProduct", new Product());
        return "admin-products";
    }

    // Lägg till ny produkt
    @PostMapping("/products")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }
    @PostMapping("/admin/orders/{id}/ship")
    public String markOrderAsShipped(@PathVariable Long id) {
        orderService.markAsShipped(id);
        return "redirect:/admin/orders";
    }
    @GetMapping("/admin/shippedorders")
    public String showShippedOrders(Model model) {
        List<Order> shippedOrders = orderRepository.findByShippedTrue();
        model.addAttribute("orders", shippedOrders);
        return "admin-shipped-orders";
    }
}
