package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
