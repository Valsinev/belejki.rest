package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.exception.RecipeIngredientNotFoundException;
import com.belejki.belejki.restful.mapper.RecipeIngredientMapper;
import com.belejki.belejki.restful.repository.IngredientRepository;
import com.belejki.belejki.restful.repository.RecipeIngredientRepository;
import com.belejki.belejki.restful.service.IngredientService;
import com.belejki.belejki.restful.service.RecipeIngredientService;
import com.belejki.belejki.restful.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeIngredientController(RecipeIngredientRepository recipeIngredientRepository, RecipeIngredientService recipeIngredientService, RecipeIngredientMapper recipeIngredientMapper, RecipeService recipeService, IngredientService ingredientService, IngredientRepository ingredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientService = recipeIngredientService;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
    }


    //region POST METHODS

    @PostMapping("/user/recipe-ingredients")
    public ResponseEntity<RecipeIngredientDto> save(@Valid @RequestBody RecipeIngredientDto recipeIngredientDto) {
        Recipe recipe = recipeService.findById(recipeIngredientDto.getRecipeId());
        Ingredient ingredient = ingredientRepository.findByName(recipeIngredientDto.getIngredient())
                .orElse(new Ingredient(recipeIngredientDto.getIngredient()));
        RecipeIngredient recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientDto, recipe, ingredient);
        RecipeIngredient saved = recipeIngredientService.save(recipeIngredient);
        RecipeIngredientDto dto = recipeIngredientMapper.toDto(saved);
        return ResponseEntity.ok(dto);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/recipe-ingredients")
    public Page<RecipeIngredientDto> findAll(Pageable pageable) {
        Page<RecipeIngredient> all = recipeIngredientRepository.findAll(pageable);
        return all.map(recipeIngredientMapper::toDto);
    }

    @GetMapping("/admin/recipe-ingredients/id/{id}")
    public RecipeIngredientDto findById(@PathVariable Long id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RecipeIngredientNotFoundException("Recipe ingredient not found for id: " + id));
        return recipeIngredientMapper.toDto(recipeIngredient);
    }

    @GetMapping("/admin/recipe-ingredients/recipe/{recipeName}")
    public Page<RecipeIngredientDto> findAllByRecipe_Name(@PathVariable String recipeName, Pageable pageable) {
        Page<RecipeIngredient> byRecipeName = recipeIngredientService.findByRecipe_Name(recipeName, pageable);
        return byRecipeName.map(recipeIngredientMapper::toDto);
    }

    @GetMapping("/user/recipe-ingredients/recipe")
    public List<RecipeIngredientDto> findAllByRecipe(@RequestBody Recipe recipe) {
        List<RecipeIngredient> byRecipe = recipeIngredientRepository.findByRecipe(recipe);
        return byRecipe.stream().map(recipeIngredientMapper::toDto).toList();
    }

    @GetMapping("/user/recipe-ingredients/recipe/id/{recipeId}")
    public List<RecipeIngredientDto> findAllByRecipeId(@PathVariable Long recipeId) {
        List<RecipeIngredient> byRecipeId = recipeIngredientRepository.findByRecipe_Id(recipeId);
        return byRecipeId.stream().map(recipeIngredientMapper::toDto).toList();
    }

    //endregion


    //region DELETE METHODS

    @DeleteMapping("/user/recipe-ingredients")
    public ResponseEntity<RecipeIngredientDto> delete(@RequestBody RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.delete(recipeIngredient);
        return ResponseEntity.ok(recipeIngredientMapper.toDto(recipeIngredient));
    }

    @DeleteMapping("/user/recipe-ingredients/id/{id}")
    public ResponseEntity<RecipeIngredientDto> deleteById(@PathVariable Long id) {
        RecipeIngredient recipeIngredient = recipeIngredientService.deleteById(id);
        return ResponseEntity.ok(recipeIngredientMapper.toDto(recipeIngredient));
    }

    @DeleteMapping("/user/recipe-ingredients/recipe")
    public ResponseEntity<List<RecipeIngredientDto>> deleteByRecipe(@RequestBody Recipe recipe) {
        List<RecipeIngredient> byRecipe = recipeIngredientService.deleteByRecipe(recipe);
        return ResponseEntity.ok(byRecipe.stream().map(recipeIngredientMapper::toDto).toList());
    }

    @DeleteMapping("/user/recipe-ingredients/recipe/id/{recipeId}")
    public ResponseEntity<List<RecipeIngredientDto>> deleteByRecipe_Id(@PathVariable Long recipeId) {
        List<RecipeIngredient> byRecipeId = recipeIngredientRepository.findByRecipe_Id(recipeId);
        recipeIngredientRepository.deleteAll(byRecipeId);
        List<RecipeIngredientDto> list = byRecipeId.stream().map(recipeIngredientMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

    //endregion
}
