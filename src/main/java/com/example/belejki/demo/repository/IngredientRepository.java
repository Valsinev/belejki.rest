package com.example.belejki.demo.repository;

import com.example.belejki.demo.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
