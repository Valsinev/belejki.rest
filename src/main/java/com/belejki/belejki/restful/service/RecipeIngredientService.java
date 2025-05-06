package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.repository.RecipeIngredientRepository;
import com.belejki.belejki.restful.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipeRepository recipeRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
    }
}
