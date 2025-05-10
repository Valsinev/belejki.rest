package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.IngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {


    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto dto = new IngredientDto();
        dto.setName(ingredient.getName());
        dto.setId(ingredient.getId());
        dto.setRecipeIngredientIds(ingredient.getRecipeIngredients().stream().map(RecipeIngredient::getId).toList());
        return dto;
    }

    public Ingredient toEntity(IngredientDto dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.getId());
        ingredient.setName(dto.getName());
        return ingredient;
    }
}
