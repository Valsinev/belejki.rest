package com.belejki.belejki.restful.shoppingItem.repository;

import com.belejki.belejki.restful.shoppingItem.domain.ShoppingItem;
import com.belejki.belejki.restful.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    List<ShoppingItem> findByUser_Username(String username);

    List<ShoppingItem> findByUser(User user);

    Page<ShoppingItem> findByUser_Username(String username, Pageable pageable);

    Page<ShoppingItem> findAllByUser_Username(String username, Pageable pageable);

    Page<ShoppingItem> findAllByUser_Id(Long userId, Pageable pageable);

    Optional<ShoppingItem> findByIdAndUser_Username(Long id, String username);

    void deleteByIdAndUser_Username(Long id, String username);

    void deleteAllByUser_Username(String username);
}
