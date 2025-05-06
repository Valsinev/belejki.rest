package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.repository.RecipeIngredientRepository;
import com.belejki.belejki.restful.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeIngredientController(RecipeIngredientRepository recipeIngredientRepository, RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientService = recipeIngredientService;
    }


    //region POST METHODS

    @PostMapping
    public ResponseEntity<RecipeIngredient> save(@RequestBody RecipeIngredient recipeIngredient) {
        RecipeIngredient save = recipeIngredientRepository.save(recipeIngredient);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    //endregion

    //region GET METHODS

    @GetMapping
    public Page<RecipeIngredient> findAll(Pageable pageable) {
        return recipeIngredientRepository.findAll(pageable);
    }

    @GetMapping("/{recipeName}")
    public List<RecipeIngredient> findByRecipe_Name(@PathVariable String recipeName) {
        return recipeIngredientRepository.findByRecipe_Name(recipeName);
    }

    @GetMapping("/recipe")
    public List<RecipeIngredient> findByRecipe(@RequestBody Recipe recipe) {
        return recipeIngredientRepository.findByRecipe(recipe);
    }

    //endregion


    //region DELETE METHODS

    @DeleteMapping
    public ResponseEntity delete(@RequestBody RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.delete(recipeIngredient);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        recipeIngredientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recipe")
    public ResponseEntity deleteByRecipe(@RequestBody Recipe recipe) {
        List<RecipeIngredient> byRecipe = recipeIngredientRepository.findByRecipe(recipe);
        recipeIngredientRepository.deleteAll(byRecipe);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeName}")
    public ResponseEntity deleteByRecipe(@PathVariable String recipeName) {
        List<RecipeIngredient> byRecipeName = recipeIngredientRepository.findByRecipe_Name(recipeName);
        recipeIngredientRepository.deleteAll(byRecipeName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recipe/{recipeId}")
    public ResponseEntity deleteByRecipe_Id(@PathVariable Long recipeId) {
        List<RecipeIngredient> byRecipeId = recipeIngredientRepository.findByRecipe_Id(recipeId);
        recipeIngredientRepository.deleteAll(byRecipeId);
        return ResponseEntity.noContent().build();
    }

    //endregion
}
