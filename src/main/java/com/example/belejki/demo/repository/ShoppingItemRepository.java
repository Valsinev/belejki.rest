package com.example.belejki.demo.repository;

import com.example.belejki.demo.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
}
