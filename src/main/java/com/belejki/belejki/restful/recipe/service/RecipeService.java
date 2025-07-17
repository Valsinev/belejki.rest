package com.belejki.belejki.restful.recipe.service;

import com.belejki.belejki.restful.recipe.web.dto.RecipeRequestDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
	RecipeResponseDto save(String username, @Valid RecipeRequestDto recipeDto);

	RecipeResponseDto findByIdAndUser_Username(Long id, String username);

	Page<RecipeResponseDto> findAllByUser_Username(String username, Pageable pageable);

	Page<RecipeResponseDto> findAllByNameContainingAndUser_Username(String recipeName, String username, Pageable pageable);

	Page<RecipeResponseDto> findRecipesByAllIngredientNamesAndUser_Username(List<String> ingredients, String username, Pageable pageable);

	Page<RecipeResponseDto> findAll(Pageable pageable);

	Page<RecipeResponseDto> findAllByNameContainingIgnoreCase(String recipeName, Pageable pageable);

	Page<RecipeResponseDto> findAllByUser_Id(Long id, Pageable pageable);

	void delete(RecipeRequestDto recipe);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByUser_Id(Long id);

	void deleteAllByUser_Username(String username);
}
