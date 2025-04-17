package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
}
