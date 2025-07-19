package com.belejki.belejki.restful.recipeIngredient.service;

import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeIngredientService {


	RecipeIngredientDto findById(Long id);

	Page<RecipeIngredientDto> findAll(Pageable pageable);

	Page<RecipeIngredientDto> findAllByRecipe(Recipe recipe, Pageable pageable);

	Page<RecipeIngredientDto> findAllByRecipe_IdAndRecipe_User_Username(Long recipeId, String username, Pageable pageable);

	void delete(@Valid RecipeIngredientDto recipeIngredient);

	void deleteById(Long id);

	void deleteByRecipe(Recipe recipe);

	void deleteAllByRecipe_Id(Long recipeId);
}
