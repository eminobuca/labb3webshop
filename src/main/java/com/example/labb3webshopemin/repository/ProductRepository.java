package com.example.labb3webshopemin.repository;

import com.example.labb3webshopemin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository för Product-objekt, hanterar all kommunikation med databasen för produkter
// JpaRepository ger grundläggande metoder som spara, hitta, ta bort osv automatiskt
public interface ProductRepository extends JpaRepository<Product, Long> {
}
