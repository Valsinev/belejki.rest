package com.example.belejki.demo.repository;

import com.example.belejki.demo.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
