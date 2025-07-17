package com.belejki.belejki.restful.recipeIngredient.mapping;

import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientFormatedRequestDto;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientRequestDto;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeIngredientMappingConfig {

	@Autowired
	public void configureRecipeIngredientMappings(ModelMapper modelMapper) {

		modelMapper.createTypeMap(RecipeIngredientRequestDto.class, RecipeIngredient.class)
				.addMappings(mapper -> {
					mapper.skip(RecipeIngredient::setRecipe);

					mapper.map(RecipeIngredientRequestDto::getId, RecipeIngredient::setId);
					mapper.map(RecipeIngredientRequestDto::getQuantity, RecipeIngredient::setQuantity);
					mapper.map(RecipeIngredientRequestDto::getIngredient, RecipeIngredient::setIngredient);
				});

		modelMapper.createTypeMap(RecipeIngredient.class, RecipeIngredientRequestDto.class)
				.addMappings(mapper -> {

					mapper.map(RecipeIngredient::getId, RecipeIngredientRequestDto::setId);
					mapper.map(RecipeIngredient::getQuantity, RecipeIngredientRequestDto::setQuantity);
					mapper.map(recIngr -> recIngr.getIngredient().getName(), RecipeIngredientRequestDto::setIngredient);
					mapper.map(recIngr -> recIngr.getRecipe().getId(), RecipeIngredientRequestDto::setRecipe);
				});


		modelMapper.createTypeMap(RecipeIngredient.class, RecipeIngredientResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(RecipeIngredient::getId, RecipeIngredientResponseDto::setId);
					mapper.map(RecipeIngredient::getQuantity, RecipeIngredientResponseDto::setQuantity);
					mapper.map(recIngr -> recIngr.getIngredient().getName(), RecipeIngredientResponseDto::setIngredient);
					mapper.map(recIngr -> recIngr.getRecipe().getId(), RecipeIngredientResponseDto::setRecipe);
				});

		modelMapper.createTypeMap(RecipeIngredientRequestDto.class, RecipeIngredientFormatedRequestDto.class)
				.addMappings(mapper -> {

					mapper.map(RecipeIngredientRequestDto::getId, RecipeIngredientFormatedRequestDto::setId);
					mapper.map(RecipeIngredientRequestDto::getQuantity, RecipeIngredientFormatedRequestDto::setQuantity);
					mapper.map(RecipeIngredientRequestDto::getRecipe, RecipeIngredientFormatedRequestDto::setRecipe);

				});

	}

}
