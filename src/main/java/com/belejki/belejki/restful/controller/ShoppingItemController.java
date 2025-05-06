package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.ShoppingItem;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.repository.ShoppingItemRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-items")
public class ShoppingItemController {

    private final ShoppingItemRepository shoppingItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShoppingItemController(ShoppingItemRepository shoppingItemRepository, UserRepository userRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.userRepository = userRepository;
    }

    //region GET METHODS

    @GetMapping
    Page<ShoppingItem> findAll(Pageable pageable) {
        return shoppingItemRepository.findAll(pageable);
    }

    @GetMapping("/{username}")
    Page<ShoppingItem> findAllByUser_Username(@PathVariable String username, Pageable pageable) {
        return shoppingItemRepository.findByUser_Username(username, pageable);
    }

    //endregion



    //region POST METHODS

    @PostMapping
    ShoppingItem save(@RequestBody ShoppingItem shoppingItem) {
        return shoppingItemRepository.save(shoppingItem);
    }

    //endregion



    //region DELETE METHODS

    @DeleteMapping
    public ResponseEntity delete(@RequestBody ShoppingItem shoppingItem) {
        shoppingItemRepository.delete(shoppingItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        shoppingItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity deleteAllByUser_Username(@PathVariable String username) {
        List<ShoppingItem> founded = shoppingItemRepository.findByUser_Username(username);
        shoppingItemRepository.deleteAll(founded);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteAllByUser(@RequestBody User user) {
        List<ShoppingItem> founded = shoppingItemRepository.findByUser(user);
        shoppingItemRepository.deleteAll(founded);
        return ResponseEntity.noContent().build();
    }

    //endregion
}

