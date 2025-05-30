package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.Product;
import com.example.labb3webshopemin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Hämta alla produkter från databasen
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Räkna antal produkter i databasen
    public long count() {
        return productRepository.count();
    }

    // Sök produkter baserat på namn (case-insensitive)
    public List<Product> searchByName(String query) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    // Spara en produkt i databasen (ny eller uppdaterad)
    public void save(Product product) {
        productRepository.save(product);
    }
}
