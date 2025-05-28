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

    @GetMapping("/products")
    public String showAllProducts(@RequestParam(required = false) String search,
                                  HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }

        if (search != null && !search.isEmpty()) {
            model.addAttribute("products", productService.searchByName(search));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }

        return "products";
    }

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

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems()); // Här heter listan cartItems
        model.addAttribute("total", cartService.getTotal());          // Totalpris som double
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/";
        }

        var cartItems = cartService.getCartItems();
        double total = cartService.getTotal();

        Order savedOrder = orderService.placeOrder(username, cartItems);
        cartService.clearCart();

        model.addAttribute("order", savedOrder);
        model.addAttribute("total", total);
        model.addAttribute("username", username);

        return "order-confirmation";
    }
}
