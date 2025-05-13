package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Recipe;
import com.belejki.belejki.restful.entity.RecipeIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    Page<RecipeIngredient> findByRecipe_Name(String recipeName, Pageable pageable);

    List<RecipeIngredient> findByRecipe(Recipe recipe);

    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
}
