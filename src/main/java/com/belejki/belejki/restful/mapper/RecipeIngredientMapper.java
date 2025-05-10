package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientMapper {


    public RecipeIngredientDto toDto(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto dto = new RecipeIngredientDto();
        dto.setId(recipeIngredient.getId());
        dto.setRecipeId(recipeIngredient.getRecipe().getId());
        dto.setIngredient(recipeIngredient.getIngredient().getName());
        dto.setQuantity(recipeIngredient.getQuantity());
        return dto;
    }

    public RecipeIngredient toEntity(@Valid RecipeIngredientDto dto, Recipe recipe, Ingredient ingredient) {

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(dto.getId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setQuantity(dto.getQuantity());
        recipeIngredient.setIngredient(ingredient);
        return recipeIngredient;
    }
}
