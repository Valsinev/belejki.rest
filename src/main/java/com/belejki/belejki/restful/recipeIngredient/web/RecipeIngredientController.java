package com.belejki.belejki.restful.recipeIngredient.web;

import com.belejki.belejki.restful.recipeIngredient.service.RecipeIngredientService;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientRequestDto;
import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientResponseDto;
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


@RestController
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;
    private final AuthService authService;

    @Autowired
	public RecipeIngredientController(RecipeIngredientService recipeIngredientService, AuthService authService) {
		this.recipeIngredientService = recipeIngredientService;
	    this.authService = authService;
    }


	//region POST METHODS

    @PostMapping("/user/recipe-ingredients")
    public ResponseEntity<RecipeIngredientResponseDto> save(@Valid @RequestBody RecipeIngredientRequestDto recipeIngredientRequestDto,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        RecipeIngredientResponseDto dto = recipeIngredientService.save(recipeIngredientRequestDto);
        return ResponseEntity.ok(dto);
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/recipe-ingredients")
    public ResponseEntity<Page<RecipeIngredientResponseDto>> findAll(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<RecipeIngredientResponseDto> all = recipeIngredientService.findAll(pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/admin/recipe-ingredients/id/{id}")
    public ResponseEntity<RecipeIngredientResponseDto> findById(@PathVariable Long id, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        RecipeIngredientResponseDto recipeIngredient = recipeIngredientService.findById(id);
        return ResponseEntity.ok(recipeIngredient);
    }

    @GetMapping("/user/recipe-ingredients/recipe")
    public ResponseEntity<Page<RecipeIngredientResponseDto>> findAllByRecipe(@RequestBody Recipe recipe, Authentication authentication, Pageable pageable) {
        Page<RecipeIngredientResponseDto> byRecipe = recipeIngredientService.findAllByRecipe(recipe, pageable);
        return ResponseEntity.ok(byRecipe);
    }

    @GetMapping("/user/recipe-ingredients/recipe/id/{recipeId}")
    public ResponseEntity<Page<RecipeIngredientResponseDto>> findAllByRecipeId(@PathVariable Long recipeId, Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<RecipeIngredientResponseDto> allByRecipeId = recipeIngredientService.findAllByRecipe_IdAndRecipe_User_Username(recipeId, username, pageable);
        return ResponseEntity.ok(allByRecipeId);
    }

    //endregion


    //region DELETE METHODS

    @DeleteMapping("/admin/recipe-ingredients")
    public ResponseEntity<Void> delete(@Valid @RequestBody RecipeIngredientRequestDto recipeIngredient, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipeIngredientService.delete(recipeIngredient);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/recipe-ingredients/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipeIngredientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/recipe-ingredients/recipe")
    public ResponseEntity<Void> deleteByRecipe(@RequestBody Recipe recipe, Authentication authentication) {
        boolean adminOrOwner = authService.checkIfOwnerOrAdminByUsername(authentication, recipe.getUser().getUsername());
        if (!adminOrOwner) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipeIngredientService.deleteByRecipe(recipe);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/adin/recipe-ingredients/recipe/id/{recipeId}")
    public ResponseEntity<Void> deleteAllByRecipe_Id(@PathVariable Long recipeId, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipeIngredientService.deleteAllByRecipe_Id(recipeId);
        return ResponseEntity.ok().build();
    }

    //endregion
}
