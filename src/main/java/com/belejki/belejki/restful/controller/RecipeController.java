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

import java.util.List;

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

    @PostMapping("/user/recipes/id/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public RecipeDto saveByUserId(@Valid @RequestBody RecipeDto recipe, @PathVariable Long userId, Authentication authentication) {
        User user = userService.findById(userId);
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Recipe save = recipeService.saveByUserId(recipe, userId);
        return recipesMapper.toDto(save, userId);
    }

    @PostMapping("/user/recipes/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public RecipeDto saveByUsername(@Valid @RequestBody RecipeDto recipe, @PathVariable String username, Authentication authentication) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Recipe save = recipeService.saveByUsername(recipe, username);
        return recipesMapper.toDto(save, save.getUser().getId());
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/recipes")
    public Page<RecipeDto> findAll(Pageable pageable) {
        Page<Recipe> all = recipeRepository.findAll(pageable);
        return all.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId()));
    }

    @GetMapping("/user/recipes/id/{id}")
    //user can only find his own recipes by id
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public RecipeDto findById(@PathVariable Long id, Authentication authentication) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(
                        () -> new RecipeNotFoundException("No recipe found with id = " + id)
                );
        String username = recipe.getUser().getUsername();
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can access this recipe.");
        }
        return recipesMapper.toDto(recipe, recipe.getUser().getId());
    }

    @GetMapping("/user/recipes")
    public Page<RecipeDto> findById(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<Recipe> allByUserUsername = recipeService.findAllByUser_Username(username, pageable);
        Page<RecipeDto> dto = allByUserUsername.map(recipe ->
                recipesMapper.toDto(recipe, recipe.getUser().getId()));
        return dto;
    }

    @GetMapping("/admin/recipes/{recipeName}")
    public List<RecipeDto> findAllByNameContainingIgnoreCase(@PathVariable String recipeName, Pageable pageable) {
        Page<Recipe> byNameContainingIgnoreCase = recipeRepository.findAllByNameContainingIgnoreCase(recipeName, pageable);
        List<RecipeDto> foundedDto = byNameContainingIgnoreCase.stream()
                .map(recipe ->
                        recipesMapper.toDto(recipe, recipe.getUser().getId()))
                .toList();
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

    @GetMapping("/user/recipes/{recipeName}")
    public List<RecipeDto> findAllOwnedByNameContainingIgnoreCase(@PathVariable String recipeName, Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<Recipe> byNameContainingIgnoreCase = recipeRepository.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        List<RecipeDto> foundedDto = byNameContainingIgnoreCase.stream()
                .map(recipe ->
                recipesMapper.toDto(recipe, recipe.getUser().getId()))
                .toList();
        return foundedDto;
    }


    @GetMapping("/user/recipes/by-ingredients")
    public Page<RecipeDto> findAllByIngredients(@RequestParam List<String> ingredients,
                                             Pageable pageable,
                                             Authentication authentication) {
        String username = authentication.getName();
        Page<Recipe> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return recipesByAllIngredientNamesAndUserUsername.map(recipe -> recipesMapper.toDto(recipe, recipe.getUser().getId()));
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
