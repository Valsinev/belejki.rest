package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.RecipeDto;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.repository.RecipeRepository;
import com.belejki.belejki.restful.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeIngredientRepository, RecipeService recipeService) {
        this.recipeRepository = recipeIngredientRepository;
        this.recipeService = recipeService;
    }


    //region POST METHODS

    @PostMapping("/user/recipes/{username}")
    public ResponseEntity<RecipeDto> save(@RequestBody RecipeDto recipe, @PathVariable Long userId) {
        return recipeService.save(recipe, userId);
    }

    //endregion

    //region GET METHODS

    @GetMapping
    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @GetMapping("/id/{id}")
    public Recipe findById(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(
                        () -> new RecipeNotFoundException("No recipe found with id = " + id)
                );
    }

    @GetMapping("/{name}")
    public Recipe findByName(@PathVariable String name) {
        return recipeRepository.findByName(name);
    }

    @GetMapping("/user/recipes/find/{name}")
    public Page<Recipe> findAllByNameContaining(@PathVariable String name, @RequestBody User user, Pageable pageable) {
        return recipeRepository.findAllByNameContainingAndUser(name, user, pageable);
    }

    @GetMapping("{username}/by-ingredients")
    public Page<Recipe> findAllByIngredients(@RequestParam List<String> ingredients,
                                             @PathVariable String username,
                                             Pageable pageable) {
        return recipeService.findRecipesByAllIngredientNamesAndUsername(ingredients, username, pageable);
    }

    @GetMapping("/by-ingredients")
    public Page<Recipe> findAllByIngredients(@RequestParam List<String> ingredients,
                                             @RequestBody User user,
                                             Pageable pageable) {
        return recipeService.findRecipesByAllIngredientNamesAndUser(ingredients, user, pageable);
    }

    //endregion

    //region DELETE METHODS

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Recipe recipe) {
        recipeRepository.delete(recipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        recipeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity deleteAllByUser_Username(@PathVariable String username) {
        List<Recipe> byUserUsername = recipeRepository.findByUser_Username(username);
        recipeRepository.deleteAll(byUserUsername);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-user")
    public ResponseEntity deleteByUser_Username(@RequestBody User user) {
        List<Recipe> byUser = recipeRepository.findByUser(user);
        recipeRepository.deleteAll(byUser);
        return ResponseEntity.noContent().build();
    }

    //endregion


}
