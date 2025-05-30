package com.example.labb3webshopemin.repository;

import com.example.labb3webshopemin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository för Order-objekt, hanterar databaskommunikation för ordrar
// Extenderar JpaRepository som ger grundläggande CRUD-operationer automatiskt
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Hämta alla ordrar som är markerade som "expedierade" (shipped = true)
    List<Order> findByShippedTrue();

    // Hämta alla ordrar som inte är expedierade (shipped = false)
    List<Order> findByShippedFalse();
}
