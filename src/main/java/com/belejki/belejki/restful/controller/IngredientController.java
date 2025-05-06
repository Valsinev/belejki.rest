package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.exception.IngredientNotFoundException;
import com.belejki.belejki.restful.repository.IngredientRepository;
import com.belejki.belejki.restful.service.IngredientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientService ingredientService, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
    }

    //region POST METHODS

    @PostMapping("/user/ingredients")
    public Ingredient save(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/user/ingredients")
    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientService.findAll(pageable);
    }

    @GetMapping("/user/ingredients/{name}")
    public Ingredient findByName(@PathVariable String name) {
        return ingredientService.findByName(name);
    }

    @GetMapping("/user/ingredients/id/{id}")
    public Ingredient findById(@PathVariable Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(String.format("Ingredient with id=%d not found.", id)));
    }

    //endregion

    //region DELETE METHODS
    @DeleteMapping("/admin/ingredients")
    public ResponseEntity delete(@RequestBody Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/ingredients/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        ingredientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/admin/ingredients/{name}")
    public ResponseEntity deleteByName(@PathVariable String name) {
        Ingredient byName = ingredientRepository.findByName(name);
        ingredientRepository.delete(byName);
        return ResponseEntity.noContent().build();
    }

    //endregion
}
