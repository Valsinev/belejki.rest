package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.RecipeDto;
import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.mapper.RecipesMapper;
import com.belejki.belejki.restful.repository.RecipeRepository;
import com.belejki.belejki.restful.service.RecipeService;
import com.belejki.belejki.restful.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.belejki.belejki.restful.controller.Utility.checkIfOwnerOrAdmin;


@RestController
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final RecipesMapper recipesMapper;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeRepository recipeIngredientRepository, RecipeService recipeService, RecipesMapper recipesMapper, UserService userService) {
        this.recipeRepository = recipeIngredientRepository;
        this.recipeService = recipeService;
        this.recipesMapper = recipesMapper;
        this.userService = userService;
    }


    //region POST METHODS

    @PostMapping("/user/recipes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public RecipeDto saveByUserId(@Valid @RequestBody RecipeDto recipe, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        recipe.setUserId(user.getId());
        Recipe saved = recipeService.saveByUserId(recipe, user);
        return recipesMapper.toDto(saved, saved.getUser().getId());
    }

    //endregion

    //region GET METHODS


    @GetMapping("/user/recipes/id/{id}")
    public RecipeDto findById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Recipe founded = recipeRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found for id: " + id));
        recipeRepository.delete(founded);
        return recipesMapper.toDto(founded, founded.getUser().getId());
    }

    @GetMapping("/user/recipes")
    public Page<RecipeDto> findAllUserRecipes(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<Recipe> allByUserUsername = recipeService.findAllByUser_Username(username, pageable);
        Page<RecipeDto> dto = allByUserUsername.map(recipe ->
                recipesMapper.toDto(recipe, recipe.getUser().getId()));
        return dto;
    }


    @GetMapping("/user/recipes/{recipeName}")
    public Page<RecipeDto> findAllOwnedByNameContainingIgnoreCase(@PathVariable String recipeName, Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<Recipe> byNameContainingIgnoreCase = recipeRepository.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        return byNameContainingIgnoreCase.map(recipe ->
                        recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }

    @GetMapping("/user/recipes/by-name-and-username")
    public Page<RecipeDto> findAllOwnedByNameContainingAndUsername(@RequestParam String recipeName,
                                                                   @RequestParam String username,
                                                                   Pageable pageable) {
        String decodedRecipeName = URLDecoder.decode(recipeName, StandardCharsets.UTF_8);

        Page<Recipe> byNameContainingIgnoreCase = recipeRepository.findAllByNameContainingAndUser_Username(decodedRecipeName, username, pageable);
        return byNameContainingIgnoreCase.map(recipe ->
                recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }


    @GetMapping("/user/recipes/by-ingredients-and-username")
    public Page<RecipeDto> findAllByIngredientsAndUsername(@RequestParam List<String> ingredients,
                                                @RequestParam String username,
                                                Pageable pageable) {
        Page<Recipe> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return recipesByAllIngredientNamesAndUserUsername.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }


    @GetMapping("/user/recipes/by-ingredients")
    public Page<RecipeDto> findAllByIngredients(@RequestParam List<String> ingredients,
                                                Pageable pageable,
                                                Authentication authentication) {
        String username = authentication.getName();
        Page<Recipe> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return recipesByAllIngredientNamesAndUserUsername.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }

    @GetMapping("/admin/recipes")
    public Page<RecipeDto> findAll(Pageable pageable) {
        Page<Recipe> all = recipeRepository.findAll(pageable);
        return all.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }

    @GetMapping("/admin/recipes/{recipeName}")
    public Page<RecipeDto> findAllByNameContainingIgnoreCase(@PathVariable String recipeName, Pageable pageable) {
        Page<Recipe> byNameContainingIgnoreCase = recipeRepository.findAllByNameContainingIgnoreCase(recipeName, pageable);
        Page<RecipeDto> foundedDto = byNameContainingIgnoreCase
                .map(recipe ->
                        recipesMapper.toDto(recipe, recipe.getUser().getId()));
        return foundedDto;
    }

    @GetMapping("/admin/recipes/user/id/{id}")
    public List<RecipeDto> findAllByUserId(@PathVariable Long id, Pageable pageable) {
        Page<Recipe> allByUserId = recipeService.findAllByUser_Id(id, pageable);
        List<RecipeDto> foundedDto = allByUserId.stream()
                .map(recipe ->
                        recipesMapper.toDto(recipe, recipe.getUser().getId()))
                .toList();
        return foundedDto;
    }

    @GetMapping("/admin/recipes/user/{username}")
    public List<RecipeDto> findAllByUserId(@PathVariable String username, Pageable pageable) {
        Page<Recipe> allByUserId = recipeService.findAllByUser_Username(username, pageable);
        List<RecipeDto> foundedDto = allByUserId.stream()
                .map(recipe ->
                        recipesMapper.toDto(recipe, recipe.getUser().getId()))
                .toList();
        return foundedDto;
    }


    //endregion

    //region DELETE METHODS

    @DeleteMapping("/user/recipes")
    public ResponseEntity<RecipeDto> delete(@RequestBody Recipe recipe) {
        Recipe deleted = recipeService.delete(recipe);
        return ResponseEntity.ok(recipesMapper.toDto(deleted, deleted.getUser().getId()));
    }

    @DeleteMapping("/user/recipes/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ResponseEntity<RecipeDto> deleteById(@PathVariable Long id, Authentication authentication) {
        Recipe recipe = recipeService.findById(id);
        boolean access = checkIfOwnerOrAdmin(authentication, recipe.getUser().getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can delete this recipe.");
        }
        Recipe deleted = recipeService.delete(recipe);
        return ResponseEntity.ok(recipesMapper.toDto(deleted, deleted.getUser().getId()));
    }

    @DeleteMapping("/admin/recipes/user/id/{id}")
    public ResponseEntity<Page<RecipeDto>> deleteAllByUser_Username(@PathVariable Long id, Pageable pageable) {
        Page<Recipe> allByUserId = recipeService.findAllByUser_Id(id, pageable);
        recipeRepository.deleteAll(allByUserId);
        return ResponseEntity.ok(allByUserId.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId())));
    }


    @DeleteMapping("/admin/recipes/user/{username}")
    public ResponseEntity<Page<RecipeDto>> deleteAllByUser_Username(@PathVariable String username, Pageable pageable) {
        Page<Recipe> allByUser_username = recipeService.findAllByUser_Username(username, pageable);
        recipeRepository.deleteAll(allByUser_username);
        return ResponseEntity.ok(allByUser_username.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId())));
    }

    //endregion


}
