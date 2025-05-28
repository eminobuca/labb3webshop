package com.example.labb3webshopemin.repository;

import com.example.labb3webshopemin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}