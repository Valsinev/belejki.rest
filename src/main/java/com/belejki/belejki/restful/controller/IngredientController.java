package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.IngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.mapper.IngredientMapper;
import com.belejki.belejki.restful.repository.IngredientRepository;
import com.belejki.belejki.restful.service.IngredientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientService ingredientService, IngredientMapper ingredientMapper, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
        this.ingredientRepository = ingredientRepository;
    }

    //region POST METHODS

    @PostMapping("/user/ingredients")
    public IngredientDto save(@Valid @RequestBody IngredientDto ingredient) {
        Ingredient newIngredient = ingredientRepository.findByName(ingredient.getName())
                .orElseGet(() -> new Ingredient(ingredient.getName()));

        ingredientService.save(ingredient);
        return ingredientMapper.toDto(newIngredient);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/ingredients")
    public Page<IngredientDto> findAll(Pageable pageable) {
        return ingredientService.findAll(pageable)
                .map(ingredientMapper::toDto);
    }

    @GetMapping("/admin/ingredients/{name}")
    public IngredientDto findByName(@PathVariable String name) {
        Ingredient byName = ingredientService.findByName(name);
        return ingredientMapper.toDto(byName);
    }

    @GetMapping("/admin/ingredients/id/{id}")
    public IngredientDto findById(@PathVariable Long id) {
        Ingredient byId = ingredientService.findById(id);
        return ingredientMapper.toDto(byId);
    }

    //endregion

    //region DELETE METHODS

    @DeleteMapping("/admin/ingredients/id/{id}")
    public ResponseEntity<IngredientDto> deleteById(@PathVariable Long id) {
        Ingredient deleted = ingredientService.deleteById(id);
        return ResponseEntity.ok(ingredientMapper.toDto(deleted));
    }

    @Transactional
    @DeleteMapping("/admin/ingredients/{name}")
    public ResponseEntity<IngredientDto> deleteByName(@PathVariable String name) {
        Ingredient deleted = ingredientService.deleteByName(name);
        return ResponseEntity.ok(ingredientMapper.toDto(deleted));
    }

    @Transactional
    @DeleteMapping("/admin/ingredients/clear")
    public ResponseEntity<Page<IngredientDto>> deleteAllWithEmptyRecipeIngredients(Pageable pageable) {
        Page<Ingredient> allWithoutRecipeIngredients = ingredientRepository.findAllWithoutRecipeIngredients(pageable);
        ingredientRepository.deleteAll(allWithoutRecipeIngredients);
        Page<IngredientDto> dto = allWithoutRecipeIngredients.map(ingredientMapper::toDto);
        return ResponseEntity.ok(dto);
    }

    //endregion
}
