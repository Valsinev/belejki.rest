package com.belejki.belejki.restful.recipeIngredient.service;

import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientRequestDto;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeIngredientService {

	RecipeIngredientResponseDto save(@Valid RecipeIngredientRequestDto recipeIngredientRequestDto);

	RecipeIngredientResponseDto findById(Long id);

	Page<RecipeIngredientResponseDto> findAll(Pageable pageable);

	Page<RecipeIngredientResponseDto> findAllByRecipe(Recipe recipe, Pageable pageable);

	Page<RecipeIngredientResponseDto> findAllByRecipe_IdAndRecipe_User_Username(Long recipeId, String username, Pageable pageable);

	void delete(@Valid RecipeIngredientRequestDto recipeIngredient);

	void deleteById(Long id);

	void deleteByRecipe(Recipe recipe);

	void deleteAllByRecipe_Id(Long recipeId);
}
