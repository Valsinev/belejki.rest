package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.ShoppingItem;
import com.belejki.belejki.restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    List<ShoppingItem> findByUser_Username(String username);

    List<ShoppingItem> findByUser(User user);

    Page<ShoppingItem> findByUser_Username(String username, Pageable pageable);

    Page<ShoppingItem> findAllByUser_Username(String username, Pageable pageable);

    Page<ShoppingItem> findAllByUser_Id(Long userId, Pageable pageable);
}
