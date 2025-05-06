package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipe_Name(String recipeName);

    List<RecipeIngredient> findByRecipe(Recipe recipe);

    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
}
