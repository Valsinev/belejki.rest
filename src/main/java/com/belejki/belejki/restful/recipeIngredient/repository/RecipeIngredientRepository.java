package com.belejki.belejki.restful.recipeIngredient.repository;

import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    Page<RecipeIngredient> findAllByRecipe_Name(String recipeName, Pageable pageable);

    Page<RecipeIngredient> findAllByRecipe(Recipe recipe, Pageable pageable);

    Page<RecipeIngredient> findAllByRecipe_IdAndRecipe_User_Username(Long recipeId, String username, Pageable pageable);

    void deleteAllByRecipe_Id(Long recipeId);
}
