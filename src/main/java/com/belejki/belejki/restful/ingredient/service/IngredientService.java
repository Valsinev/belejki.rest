package com.belejki.belejki.restful.ingredient.service;

import com.belejki.belejki.restful.ingredient.web.dto.IngredientDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IngredientService {

	IngredientDto save(@Valid IngredientDto ingredient);

	IngredientDto findById(Long id);

	Optional<IngredientDto> findByName(@NotBlank String name);

	Page<IngredientDto> findAll(Pageable pageable);

	void deleteById(Long id);

	void deleteByName(String name);

	void deleteAllWithoutRecipeIngredients();
}
