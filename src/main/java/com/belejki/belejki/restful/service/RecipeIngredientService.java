package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.exception.RecipeIngredientNotFoundException;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.repository.RecipeIngredientRepository;
import com.belejki.belejki.restful.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, IngredientService ingredientService) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientService = ingredientService;
    }

    public RecipeIngredient save(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public Page<RecipeIngredient> findByRecipe_Name(String recipeName, Pageable pageable) {
        return recipeIngredientRepository.findByRecipe_Name(recipeName, pageable);
    }

    public RecipeIngredient findById(Long id) {
        return recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RecipeIngredientNotFoundException("Recipe ingredient not found for id: " + id));
    }

    public RecipeIngredient deleteById(Long id) {
        RecipeIngredient byId = findById(id);
        recipeIngredientRepository.delete(byId);
        return byId;
    }

    public List<RecipeIngredient> deleteByRecipe(Recipe recipe) {
        List<RecipeIngredient> byRecipe = recipeIngredientRepository.findByRecipe(recipe);
        recipeIngredientRepository.deleteAll(byRecipe);
        return byRecipe;
    }
}
