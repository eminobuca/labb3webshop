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

@Controller
@RequestMapping("/admin") // Alla routes här börjar med /admin, så vi samlar adminfunktioner här
public class AdminController {

    @Autowired
    private OrderService orderService; // Här använder vi OrderService för att hämta och ändra ordrar

    @Autowired
    private ProductService productService; // ProductService för att hantera produkter

    // Visa alla ordrar som inte har blivit expedierade ännu (alltså oexpedierade)
    @GetMapping("/orders")
    public String viewUnshippedOrders(Model model) {
        // Vi hämtar alla oexpedierade ordrar via service
        List<Order> unshippedOrders = orderService.getUnshippedOrders();

        // För varje order räknar vi ut totalpriset (antal gånger pris per produkt)
        // och sparar det i order-objektet så vi kan visa det i vyn
        for (Order order : unshippedOrders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }

        // Lägger in listan med ordrar i modellen så Thymeleaf kan visa dem
        model.addAttribute("orders", unshippedOrders);

        // Vi skickar tillbaka namnet på vyn som ska användas (admin-orders.html)
        return "admin-orders";
    }

    // Visa alla ordrar som redan har blivit expedierade
    @GetMapping("/orders/shipped")
    public String viewShippedOrders(Model model) {
        // Hämtar ordrar som är markerade som expedierade
        List<Order> shippedOrders = orderService.getShippedOrders();

        // Precis som för oexpedierade ordrar räknar vi totalpriset för varje order
        for (Order order : shippedOrders) {
            double totalPrice = order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();
            order.setTotalPrice(totalPrice);
        }

        // Lägger in listan med expedierade ordrar i modellen för visning
        model.addAttribute("orders", shippedOrders);

        // Returnerar vyn som visar expedierade ordrar (admin-shipped-orders.html)
        return "admin-shipped-orders";
    }

    // När admin klickar på "markera som expedierad" ska denna route aktiveras
    @PostMapping("/orders/{id}/ship")
    public String markOrderAsShipped(@PathVariable Long id) {
        // Vi ber OrderService att uppdatera ordern med detta ID så att den blir expedierad
        orderService.markAsShipped(id);

        // Efteråt skickar vi admin tillbaka till sidan med oexpedierade ordrar för att se uppdateringen
        return "redirect:/admin/orders";
    }

    // Visa produkthanteringssidan där admin kan se alla produkter och lägga till nya
    @GetMapping("/products")
    public String showAdminProductPage(Model model) {
        // Hämta alla produkter för att visa dem i listan
        model.addAttribute("products", productService.getAllProducts());

        // Skapar en tom produkt som kan fyllas i av admin via formuläret
        model.addAttribute("newProduct", new Product());

        // Returnerar vyn admin-products.html där admin kan hantera produkterna
        return "admin-products";
    }

    // När admin skickar in formuläret för att lägga till en produkt
    @PostMapping("/products")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        // Sparar produkten i databasen via service-lagret
        productService.save(product);

        // Skickar admin tillbaka till produktsidan så att listan uppdateras
        return "redirect:/admin/products";
    }
}
