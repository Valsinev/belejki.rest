package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
}
