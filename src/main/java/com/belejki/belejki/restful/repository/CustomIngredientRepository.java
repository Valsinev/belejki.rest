package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.RecipeIngredient;

public interface CustomIngredientRepository {
    RecipeIngredient saveIfNotExists(RecipeIngredient ingredient);
}

