package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.RecipeDto;
import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.mapper.RecipeIngredientMapper;
import com.belejki.belejki.restful.mapper.RecipesMapper;
import com.belejki.belejki.restful.repository.RecipeRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RecipesMapper recipesMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, UserService userService, RecipesMapper recipesMapper, RecipeIngredientMapper recipeIngredientMapper, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.recipesMapper = recipesMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.ingredientService = ingredientService;
    }

    public Page<Recipe> findRecipesByAllIngredientNamesAndUser(List<String> ingredients, User user, Pageable pageable) {
        return recipeRepository.findRecipesByAllIngredientNamesAndUser(ingredients, ingredients.size(), user, pageable);
    }

    public Page<Recipe> findRecipesByAllIngredientNamesAndUsername(List<String> ingredients, String username, Pageable pageable) {
        return recipeRepository.findRecipesByAllIngredientNamesAndUsername(ingredients, ingredients.size(), username, pageable);
    }

    public Recipe save(RecipeDto dto, Long userId) {

        User user = userService.findById(userId);
        Recipe recipe = new Recipe();
        List<RecipeIngredientDto> recipeIngredientsDto = dto.getIngredients();
        List<RecipeIngredient> recipeIngredients = recipeIngredientsDto
                .stream()
                .map(recipeIngredientDto ->
                        recipeIngredientMapper.toEntity(recipeIngredientDto,
                                recipe,
                                ingredientService.findOrCreateByName(recipeIngredientDto.getIngredient())))
                .toList();
        Recipe finalRecipie = recipesMapper.toEntity(dto, user, recipeIngredients);
        recipeRepository.save(finalRecipie);
        return finalRecipie;
    }

    public Recipe findById(@NonNull Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException("Recipe not found for id: " + recipeId));
    }
}
