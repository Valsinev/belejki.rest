package com.belejki.belejki.restful.recipe.web;

import com.belejki.belejki.restful.recipe.service.RecipeService;
import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByIngredientsAndUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.FriendRecipesByUsernameDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeDto;
import com.belejki.belejki.restful.shared.AuthService;
import jakarta.validation.Valid;
import org.apache.http.auth.AUTH;
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
    public ResponseEntity<RecipeDto> save(@Valid @RequestBody RecipeDto recipeDto,
                                                          BindingResult bindingResult,
                                                          Authentication authentication) {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().body(recipeDto);
        }
        String username = authentication.getName();

        RecipeDto saved = recipeService.save(username, recipeDto);
        return ResponseEntity.ok(saved);
    }

    //endregion

    //region GET METHODS


    @GetMapping("/user/recipes/id/{id}")
    public ResponseEntity<RecipeDto> findById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        RecipeDto founded = recipeService.findByIdAndUser_Username(id, username);
        return ResponseEntity.ok(founded);
    }

    @GetMapping("/user/recipes")
    public ResponseEntity<Page<RecipeDto>> findAllUserRecipes(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<RecipeDto> allByUserUsername = recipeService.findAllByUser_Username(username, pageable);
        return ResponseEntity.ok(allByUserUsername);
    }


    @GetMapping("/user/recipes/{recipeName}")
    public ResponseEntity<Page<RecipeDto>> findAllOwnedByNameContainingIgnoreCase(@PathVariable String recipeName, Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<RecipeDto> allByNameContainingIgnoreCase = recipeService.findAllByNameContainingAndUser_Username(recipeName, username, pageable);
        return ResponseEntity.ok(allByNameContainingIgnoreCase);
    }

    @GetMapping("/user/friend/recipes")
    public ResponseEntity<Page<RecipeDto>> findAllFriendsRecipesByNameContaining(@Valid @RequestBody FriendRecipesByUsernameDto friendRecipesByUsernameDto,
                                                                                   BindingResult bindingResult,
                                                                                   Authentication authentication,
                                                                                   Pageable pageable) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        String username = authentication.getName();

        Page<RecipeDto> byNameContainingIgnoreCase = recipeService.findAllFriendRecipesByName(friendRecipesByUsernameDto, username, pageable);
        return ResponseEntity.ok(byNameContainingIgnoreCase);
    }


    @GetMapping("/user/friend/recipes/by-ingredients-and-username")
    public ResponseEntity<Page<RecipeDto>> findAllByIngredientsAndUsername(@Valid @RequestBody FriendRecipesByIngredientsAndUsernameDto friendRecipesRequestDto,
                                                                   BindingResult bindingResult,
                                                                   Authentication authentication,
                                                                   Pageable pageable) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        String username = authentication.getName();

        Page<RecipeDto> recipesByAllIngredientNamesAndUserUsername = recipeService.findAllFriendRecipesByIngredients(friendRecipesRequestDto, username, pageable);
        return ResponseEntity.ok(recipesByAllIngredientNamesAndUserUsername);
    }


    @GetMapping("/user/recipes/by-ingredients")
    public ResponseEntity<Page<RecipeDto>> findAllByIngredients(@RequestParam List<String> ingredients,
                                                Pageable pageable,
                                                Authentication authentication) {
        String username = authentication.getName();
        Page<RecipeDto> recipesByAllIngredientNamesAndUserUsername = recipeService.findRecipesByAllIngredientNamesAndUser_Username(ingredients, username, pageable);
        return ResponseEntity.ok(recipesByAllIngredientNamesAndUserUsername);
    }

    @GetMapping("/admin/recipes")
    public ResponseEntity<Page<RecipeDto>> findAll(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeDto> all = recipeService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/recipes/{recipeName}")
    public ResponseEntity<Page<RecipeDto>> findAllByNameContainingIgnoreCase(@PathVariable String recipeName, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeDto> byNameContainingIgnoreCase = recipeService.findAllByNameContainingIgnoreCase(recipeName, pageable);
        return ResponseEntity.ok(byNameContainingIgnoreCase);
    }

    @GetMapping("/admin/recipes/user/id/{id}")
    public ResponseEntity<Page<RecipeDto>> findAllByUserId(@PathVariable Long id, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeDto> allByUserId = recipeService.findAllByUser_Id(id, pageable);
        return ResponseEntity.ok(allByUserId);
    }

    @GetMapping("/admin/recipes/user/{username}")
    public ResponseEntity<Page<RecipeDto>> findAllByUser_Username(@PathVariable String username, Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeDto> allByUserId = recipeService.findAllByUser_Username(username, pageable);
        return ResponseEntity.ok(allByUserId);
    }


    //endregion

    //region DELETE METHODS

    @DeleteMapping("/admin/recipes")
    public ResponseEntity<Void> delete(@RequestBody RecipeDto recipe) {

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
