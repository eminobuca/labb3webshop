package com.example.labb3webshopemin;

import com.example.labb3webshopemin.service.UserService;
import com.example.labb3webshopemin.model.Product;
import com.example.labb3webshopemin.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

// Detta är huvudklassen som startar hela Spring Boot-applikationen
@SpringBootApplication
public class Labb3WebShopEminApplication {

    // Detta är "main-metoden" som kör applikationen
    public static void main(String[] args) {
        SpringApplication.run(Labb3WebShopEminApplication.class, args);
    }

    // Den här metoden körs automatiskt när applikationen startar
    // Används för att lägga in initial data i databasen
    @Bean
    public CommandLineRunner init(@Autowired UserService userService, @Autowired ProductService productService) {
        return args -> {
            // Skapa en admin-användare automatiskt när applikationen startar
            userService.register("admin", "admin123", "admin");

            // Kolla om databasen är tom på produkter
            if (productService.count() == 0) {
                // Om databasen inte har några produkter (alltså är tom),
                // så skapar vi och sparar några exempelprodukter automatiskt.
                // På så sätt finns det produkter att visa i shoppen direkt vid start.
                productService.save(new Product("Hoodie", "Vit bomullshoodie", 120.00, 30));
                productService.save(new Product("T-shirt", "Svart T-shirt av bomull", 180.00, 12));
                productService.save(new Product("Kepa", "Röd kepa med logga", 200.00, 5));
                productService.save(new Product("Ryggsäck", "Blå sportryggsäck 25L", 350.00, 10));
                productService.save(new Product("Sneakers", "Vita sneakers i läder", 899.00, 8));
                productService.save(new Product("Vattenflaska", "Metallflaska 750ml", 149.00, 25));
                productService.save(new Product("Träningsbyxor", "Svarta träningsbyxor i stretchmaterial", 299.00, 15));
                productService.save(new Product("Jacka", "Vindjacka med reflex", 649.00, 7));
                productService.save(new Product("Mössa", "Stickad mössa i ull", 99.00, 18));
                productService.save(new Product("Solglasögon", "Polariserade solglasögon", 229.00, 20));
            }
        };
    }

}
