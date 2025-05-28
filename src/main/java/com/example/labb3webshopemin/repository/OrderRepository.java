package com.example.labb3webshopemin.repository;

import com.example.labb3webshopemin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByShipped(boolean shipped);
    List<Order> findByShippedTrue();
    List<Order> findByShippedFalse();
}
