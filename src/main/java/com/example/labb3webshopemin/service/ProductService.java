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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public long count() {
        return productRepository.count();
    }
    public List<Product> searchByName(String query) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }


    public void save(Product product) {
        productRepository.save(product);
    }
}