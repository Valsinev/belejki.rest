package com.belejki.belejki.restful.ingredient.mapping;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredientMappingConfig {

	@Autowired
	public void configureIngredientMappings(ModelMapper modelMapper) {

		modelMapper.createTypeMap(IngredientRequestDto.class, Ingredient.class)
				.addMappings(mapper -> {
					mapper.skip(Ingredient::setRecipeIngredients);

					mapper.map(IngredientRequestDto::getId, Ingredient::setId);
					mapper.map(IngredientRequestDto::getName, Ingredient::setName);
				});

		modelMapper.createTypeMap(Ingredient.class, IngredientResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(Ingredient::getId, IngredientResponseDto::setId);
					mapper.map(Ingredient::getName, IngredientResponseDto::setName);
				});

	}

}
