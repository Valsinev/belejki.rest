package com.belejki.belejki.restful.ingredient.service;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientDto;
import com.belejki.belejki.restful.shared.exception.IngredientNotFoundException;
import com.belejki.belejki.restful.ingredient.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public IngredientServiceImpl(IngredientRepository ingredientRepository,
	                             ModelMapper modelMapper) {
		this.ingredientRepository = ingredientRepository;
		this.modelMapper = modelMapper;
	}

	public IngredientDto save(IngredientDto ingredient) {

		Ingredient findOrSaved = ingredientRepository.findByName(ingredient.getName())
				.orElseGet(() -> ingredientRepository.save(new Ingredient(ingredient.getName())));

		return modelMapper.map(findOrSaved, IngredientDto.class);
	}

	public Page<IngredientDto> findAll(Pageable pageable) {
		Page<Ingredient> all = ingredientRepository.findAll(pageable);
		return all.map((element) -> modelMapper.map(element, IngredientDto.class));
	}

	public Optional<IngredientDto> findByName(String name) {

		Optional<Ingredient> ingredient = ingredientRepository.findByNameIgnoreCase(name);
		return ingredient.map(value -> modelMapper.map(value, IngredientDto.class));
	}

	public IngredientDto findById(Long id) {
		Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException("No ingredient found for id: " + id));
		return modelMapper.map(ingredient, IngredientDto.class);
	}

	@Transactional
	public void delete(Ingredient ingredient) {
		this.checkIfIngredientBelongsToRecipe(ingredient);
		ingredientRepository.delete(ingredient);
	}

	@Transactional
	public void deleteById(Long id) {
		ingredientRepository.deleteById(id);
	}


	@Transactional
	public void deleteByName(String name) {
		ingredientRepository.deleteByName(name);
	}

	@Transactional
	@Override
	public void deleteAllWithoutRecipeIngredients() {

		ingredientRepository.deleteAllWithoutRecipeIngredients();
	}


	private void checkIfIngredientBelongsToRecipe(Ingredient byId) {
		if (!byId.getRecipeIngredients().isEmpty()) {
			List<Long> usedInRecipeIds = byId.getRecipeIngredients().stream().map(recipeIngredient -> recipeIngredient.getRecipe().getId()).toList();
			throw new RuntimeException("Ingredient is used in recipes:" + usedInRecipeIds);
		}
	}

}
