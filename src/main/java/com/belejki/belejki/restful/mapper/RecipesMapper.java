package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.RecipeDto;
import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipesMapper {
    private final RecipeIngredientMapper recipeIngredientMapper;

    @Autowired
    public RecipesMapper(RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    public RecipeDto toDto(Recipe recipe, Long userId) {

        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setUserId(userId);
        dto.setName(recipe.getName());
        dto.setHowToMake(recipe.getHowToMake());
        //map RecipeIngredient entities to RecipeIngredientDtos
        dto.setIngredients(MapperUtility.entitiesMapper(recipe.getRecipeIngredients(), recipeIngredientMapper::toDto));

        return dto;
    }

    public Recipe toEntity(RecipeDto dto, User user, List<RecipeIngredient> recipeIngredients) {
        Recipe recipe = new Recipe();
        recipe.setId(dto.getId());
        recipe.setUser(user);
        recipe.setName(dto.getName());
        recipe.setHowToMake(dto.getHowToMake());
        recipeIngredients.forEach(recipeIngredient -> recipeIngredient.setRecipe(recipe));
        //map RecipeIngredientDtos to RecipeIngredient entity
        recipe.setRecipeIngredients(recipeIngredients);

        return recipe;
    }
}
