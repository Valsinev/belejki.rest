package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);

    Ingredient findByNameIgnoreCase(String ingredientName);
}
