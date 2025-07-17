package com.belejki.belejki.restful.ingredient.service;

import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IngredientService {

	IngredientResponseDto save(@Valid IngredientRequestDto ingredient);

	IngredientResponseDto findById(Long id);

	IngredientResponseDto findByName(@NotBlank String name);

	Page<IngredientResponseDto> findAll(Pageable pageable);

	void deleteById(Long id);

	void deleteByName(String name);

	void deleteAllWithoutRecipeIngredients();
}
