package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.entity.Wish;
import com.belejki.belejki.restful.repository.WishRepository;
import com.belejki.belejki.restful.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class WishController {

    private final WishRepository wishRepository;
    private final WishService wishService;

    @Autowired
    public WishController(WishRepository wishRepository, WishService wishService) {
        this.wishRepository = wishRepository;
        this.wishService = wishService;
    }

    //region GET METHODS

    @GetMapping("/wishes")
    public List<Wish> findAll() {
        return wishRepository.findAll();
    }

    @GetMapping("/{username}/wishlist")
    public List<Wish> findAllByUser_Username(@PathVariable String username) {
        return wishRepository.findByUser_Username(username);
    }

    @GetMapping("/wishlist")
    public List<Wish> findAllByUser(@RequestBody User user) {
        return wishRepository.findByUser(user);
    }

    //endregion

    //region POST METHODS

    @PostMapping("/wishlist")
    public Wish save(@RequestBody Wish wish) {
        return wishRepository.save(wish);
    }

    //endregion



    //region DELETE METHODS

    @DeleteMapping("/wishlist")
    public ResponseEntity delete(@RequestBody Wish wish) {
        wishRepository.delete(wish);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/wishlist/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        wishRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/wishlist/{description}")
    public ResponseEntity deleteByDescription(@PathVariable String description) {
        wishRepository.findByDescription(description);
        return ResponseEntity.noContent().build();
    }

    //endregion
}
