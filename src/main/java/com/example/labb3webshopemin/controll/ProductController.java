package com.example.labb3webshopemin.controll;

import com.example.labb3webshopemin.model.Product;
import com.example.labb3webshopemin.model.Order;
import com.example.labb3webshopemin.service.ProductService;
import com.example.labb3webshopemin.service.CartService;
import com.example.labb3webshopemin.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // Visa alla produkter, med möjlighet att söka på namn
    @GetMapping("/products")
    public String showAllProducts(@RequestParam(required = false) String search,
                                  HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        // Om användaren inte är inloggad, skicka tillbaka till startsidan
        if (session == null || session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }

        // Om sökparameter finns, visa sökresultat, annars visa alla produkter
        if (search != null && !search.isEmpty()) {
            model.addAttribute("products", productService.searchByName(search));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }

        return "products";
    }

    // Lägg till en produkt i varukorgen baserat på produkt-id
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {
        Product product = productService.getAllProducts()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (product != null) {
            cartService.addToCart(product);
        }
        return "redirect:/products";
    }

    // Visa varukorgen med alla varor och totalpris
    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems()); // Lista med alla varor i varukorgen
        model.addAttribute("total", cartService.getTotal());          // Totalt pris för varukorgen
        return "cart";
    }

    // Uppdatera antal för en produkt i varukorgen
    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // Ta bort en produkt från varukorgen
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    // Checka ut och skapa en order, töm varukorgen efteråt
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        // Om inte inloggad, skicka tillbaka till startsidan
        if (username == null) {
            return "redirect:/";
        }

        var cartItems = cartService.getCartItems();
        double total = cartService.getTotal();

        // Spara order i databasen
        Order savedOrder = orderService.placeOrder(username, cartItems);

        // Töm varukorgen efter köp
        cartService.clearCart();

        // Skicka med order, totalpris och användarnamn till bekräftelsesidan
        model.addAttribute("order", savedOrder);
        model.addAttribute("total", total);
        model.addAttribute("username", username);

        return "order-confirmation";
    }
}
