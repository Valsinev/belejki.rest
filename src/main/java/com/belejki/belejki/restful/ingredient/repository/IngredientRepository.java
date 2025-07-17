package com.belejki.belejki.restful.ingredient.repository;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findByNameIgnoreCase(String ingredientName);

    @Query("SELECT i FROM Ingredient i WHERE i.recipeIngredients IS EMPTY")
    Page<Ingredient> findAllWithoutRecipeIngredients(Pageable pageable);


    void deleteByName(String name);

    @Modifying
    @Query("DELETE FROM Ingredient i WHERE i.recipeIngredients IS EMPTY")
    void deleteAllWithoutRecipeIngredients();
}
