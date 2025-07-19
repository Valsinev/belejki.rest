package com.belejki.belejki.restful.recipe.service;

import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByIngredientsAndUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
	RecipeDto save(String username, @Valid RecipeDto recipeDto);

	RecipeDto findByIdAndUser_Username(Long id, String username);

	Page<RecipeDto> findAllByUser_Username(String username, Pageable pageable);

	Page<RecipeDto> findAllByNameContainingAndUser_Username(String recipeName, String username, Pageable pageable);

	Page<RecipeDto> findRecipesByAllIngredientNamesAndUser_Username(List<String> ingredients, String username, Pageable pageable);

	Page<RecipeDto> findAll(Pageable pageable);

	Page<RecipeDto> findAllByNameContainingIgnoreCase(String recipeName, Pageable pageable);

	Page<RecipeDto> findAllByUser_Id(Long id, Pageable pageable);

	void delete(RecipeDto recipe);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByUser_Id(Long id);

	void deleteAllByUser_Username(String username);

	Page<RecipeDto> findAllFriendRecipesByName(@Valid FriendRecipesByUsernameDto friendRecipesByUsernameDto, String username, Pageable pageable);

	Page<RecipeDto> findAllFriendRecipesByIngredients(@Valid FriendRecipesByIngredientsAndUsernameDto friendRecipesRequestDto, String username, Pageable pageable);
}
