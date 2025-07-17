package com.belejki.belejki.restful.recipe.web;

import com.belejki.belejki.restful.recipe.service.RecipeService;
import com.belejki.belejki.restful.recipe.web.dto.RecipeRequestDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeResponseDto;
import com.belejki.belejki.restful.shared.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final AuthService authService;

    @Autowired
	public RecipeController(RecipeService recipeService, AuthService authService) {
		this.recipeService = recipeService;
		this.authService = authService;
    }


	//region POST METHODS

    @PostMapping("/user/recipes")
    public ResponseEntity<RecipeResponseDto> saveByUserId(@Valid @RequestBody RecipeRequestDto recipeDto,
                                                          BindingResult bindingResult,
                                                          Authentication authentication) {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().body(recipeDto);
        }
        String username = authentication.getName();

        RecipeResponseDto saved = recipeService.save(username, recipeDto);
        return ResponseEntity.ok(saved);
    }

    //endregion

    //region GET METHODS


    @GetMapping("/user/recipes/id/{id}")
    public ResponseEntity<RecipeResponseDto> findById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        RecipeResponseDto founded = recipeService.findByIdAndUser_Username(id, username);
        return ResponseEntity.ok(founded);
    }

    @GetMapping("/user/recipes")
    public ResponseEntity<Page<RecipeResponseDto>> findAllUserRecipes(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<RecipeResponseDto> allByUserUsername = recipeService.findAllByUser_Username(username, pageable);
        return ResponseEntity.ok(allByUserUsername);
    }


    @GetMapping("/user/recipes/{recipeName}")
    public ResponseEntity<Page<RecipeResponseDto>> findAllOwnedByNameContainingIgnoreCase(@PathVariable String recipeName, Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<RecipeResponseDto> allByNameContainingIgnoreCase = recipeService.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        return ResponseEntity.ok(allByNameContainingIgnoreCase);
    }

    @GetMapping("/user/recipes/by-name-and-username")
    public ResponseEntity<Page<RecipeResponseDto>> findAllOwnedByNameContainingAndUsername(@RequestParam String recipeName,
                                                                   @RequestParam String username,
                                                                   Pageable pageable) {

        String decodedRecipeName = URLDecoder.decode(recipeName, StandardCharsets.UTF_8);
        Page<RecipeResponseDto> byNameContainingIgnoreCase = recipeService.findAllByNameContainingAndUser_Username(decodedRecipeName, username, pageable);
        return ResponseEntity.ok(byNameContainingIgnoreCase);
    }


    @GetMapping("/user/recipes/by-ingredients-and-username")
    public ResponseEntity<Page<RecipeResponseDto>> findAllByIngredientsAndUsername(@RequestParam List<String> ingredients,
                                                                   @RequestParam String username,
                                                                   Pageable pageable) {

        Page<RecipeResponseDto> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return ResponseEntity.ok(recipesByAllIngredientNamesAndUserUsername);
    }


    @GetMapping("/user/recipes/by-ingredients")
    public ResponseEntity<Page<RecipeResponseDto>> findAllByIngredients(@RequestParam List<String> ingredients,
                                                Pageable pageable,
                                                Authentication authentication) {
        String username = authentication.getName();
        Page<RecipeResponseDto> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return ResponseEntity.ok(recipesByAllIngredientNamesAndUserUsername);
    }

    @GetMapping("/admin/recipes")
    public ResponseEntity<Page<RecipeResponseDto>> findAll(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeResponseDto> all = recipeService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/recipes/{recipeName}")
    public ResponseEntity<Page<RecipeResponseDto>> findAllByNameContainingIgnoreCase(@PathVariable String recipeName, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeResponseDto> byNameContainingIgnoreCase = recipeService.findAllByNameContainingIgnoreCase(recipeName, pageable);
        return ResponseEntity.ok(byNameContainingIgnoreCase);
    }

    @GetMapping("/admin/recipes/user/id/{id}")
    public ResponseEntity<Page<RecipeResponseDto>> findAllByUserId(@PathVariable Long id, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeResponseDto> allByUserId = recipeService.findAllByUser_Id(id, pageable);
        return ResponseEntity.ok(allByUserId);
    }

    @GetMapping("/admin/recipes/user/{username}")
    public ResponseEntity<Page<RecipeResponseDto>> findAllByUser_Username(@PathVariable String username, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeResponseDto> allByUserId = recipeService.findAllByUser_Username(username, pageable);
        return ResponseEntity.ok(allByUserId);
    }


    //endregion

    //region DELETE METHODS

    @DeleteMapping("/user/recipes")
    public ResponseEntity<Void> delete(@RequestBody RecipeRequestDto recipe) {
        recipeService.delete(recipe);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/recipes/{id}")
    public ResponseEntity<Void> deleteByIdAndUser_Username(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        recipeService.deleteByIdAndUser_Username(id, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/recipes/user/id/{id}")
    public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable Long id, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipeService.deleteAllByUser_Id(id);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/admin/recipes/user/{username}")
    public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable String username) {

        recipeService.deleteAllByUser_Username(username);
        return ResponseEntity.ok().build();
    }

    //endregion



}
