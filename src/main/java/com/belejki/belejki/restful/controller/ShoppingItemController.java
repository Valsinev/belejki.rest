package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.ShoppingItem;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.repository.ShoppingItemRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ShoppingItemController {

    ShoppingItemRepository shoppingItemRepository;
    UserRepository userRepository;

    @Autowired
    public ShoppingItemController(ShoppingItemRepository shoppingItemRepository, UserRepository userRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{username}/shoppingItems")
    List<ShoppingItem> getAllShoppingItems(@PathVariable String username) {
        Optional<User> byId = userRepository.findById(username);
        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return byId.get().getShoppingItems();
    }

    @PostMapping(path = "/users/{username}/shoppingItems")
    ShoppingItem addNewItem(@PathVariable String username,  @RequestBody ShoppingItem newItem) {
        if (newItem == null) {
            throw new NullPointerException("shopping item cannot be null!");
        }
        Optional<User> byId = userRepository.findById(username);
        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        newItem.setUser(byId.get());
        return shoppingItemRepository.save(newItem);
    }
}
