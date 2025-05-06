package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.RecipeDto;
import com.belejki.belejki.restful.dto.RecipeIngredientDto;
import com.belejki.belejki.restful.entity.Ingredient;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.RecipeIngredientNullOrEmptyException;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.repository.IngredientRepository;
import com.belejki.belejki.restful.repository.RecipeRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Page<Recipe> findRecipesByAllIngredientNamesAndUser(List<String> ingredients, User user, Pageable pageable) {
        return recipeRepository.findRecipesByAllIngredientNamesAndUser(ingredients, ingredients.size(), user, pageable);
    }

    public Page<Recipe> findRecipesByAllIngredientNamesAndUsername(List<String> ingredients, String username, Pageable pageable) {
        return recipeRepository.findRecipesByAllIngredientNamesAndUsername(ingredients, ingredients.size(), username, pageable);
    }

    public ResponseEntity<RecipeDto> save(RecipeDto recipeDto, Long userId) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found."));

            checkForEmptyNullFields(recipeDto);

            Recipe recipe = new Recipe();
            recipe.setUser(user);
            recipe.setName(recipeDto.getName());
            recipe.setHowToMake(recipeDto.getHowToMake());

            //saves new ingredients if dont exist and assign them to the passed recipe
            saveNewIngredientIfDontExists(recipeDto, recipe);

            //update user's recipes
            user.getRecipes().add(recipe);
            userRepository.save(user);

            RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();

            RecipeDto saved = new RecipeDto();

            saved.setName(recipe.getName());
            saved.setHowToMake(recipe.getHowToMake());
                    saved.setIngredients(recipe.getRecipeIngredients().stream()
                                    .map(recipeIngredient ->
                                    new RecipeIngredientDto(recipeIngredient.getIngredient().getName(), recipeIngredient.getQuantity()))
                                .toList());

            return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    private void checkForEmptyNullFields(RecipeDto recipeDto) {
        checkNullOrEmpty(recipeDto.getName(),"Cannot save new Recipe without name.");
        checkNullOrEmpty(recipeDto.getHowToMake(),"Cannot save new Recipe how to make instructions.");
        if (recipeDto.getIngredients() == null || recipeDto.getIngredients().isEmpty()) {
            throw new RuntimeException("Cannot save new Recipe without ingredients.");
        }
        for (RecipeIngredientDto recipeIngredientDto: recipeDto.getIngredients()) {
            checkNullOrEmpty(recipeIngredientDto.getName(), "Cannot save ingredient with no name for recipe: " + recipeDto.getName());
            checkNullOrEmpty(recipeIngredientDto.getQuantity(), "Cannot save ingredient with no quantity for recipe: " + recipeDto.getName());
        }
    }

    private void checkNullOrEmpty(String string, String message) {
        if (string == null || string.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    private void saveNewIngredientIfDontExists(RecipeDto recipeDto, Recipe recipe) {
        for (RecipeIngredientDto recipeIngredientDto : recipeDto.getIngredients()) {
            Ingredient byName = ingredientRepository.findByNameIgnoreCase(recipeIngredientDto.getName());
            if (byName == null) {
                Ingredient newIng = new Ingredient();
                newIng.setName(recipeIngredientDto.getName());
                ingredientRepository.save(newIng);
                recipe.addIngredient(newIng, recipeIngredientDto.getQuantity());
            } else {
                recipe.addIngredient(byName, recipeIngredientDto.getQuantity());
            }
        }
    }
}
