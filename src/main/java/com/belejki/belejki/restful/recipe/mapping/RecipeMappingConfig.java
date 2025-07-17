package com.belejki.belejki.restful.recipe.mapping;

import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.recipe.web.dto.RecipeRequestDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeMappingConfig {

	@Autowired
	public void configureRecipeIngredientMappings(ModelMapper modelMapper) {

		modelMapper.createTypeMap(RecipeRequestDto.class, Recipe.class)
				.addMappings(mapper -> {
					mapper.skip(Recipe::setUser);

					mapper.map(RecipeRequestDto::getId, Recipe::setId);
					mapper.map(RecipeRequestDto::getName, Recipe::setName);
					mapper.map(RecipeRequestDto::getInstructions, Recipe::setInstructions);
					mapper.map(RecipeRequestDto::getVideoLink, Recipe::setVideoLink);
					mapper.map(RecipeRequestDto::getIngredients, Recipe::setRecipeIngredients);
				});


		modelMapper.createTypeMap(Recipe.class, RecipeResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(Recipe::getId, RecipeResponseDto::setId);
					mapper.map(Recipe::getName, RecipeResponseDto::setName);
					mapper.map(Recipe::getInstructions, RecipeResponseDto::setInstructions);
					mapper.map(Recipe::getVideoLink, RecipeResponseDto::setVideoLink);
					mapper.map(Recipe::getRecipeIngredients, RecipeResponseDto::setIngredients);
					mapper.map(recipe -> recipe.getUser().getId(), RecipeResponseDto::setUser);

				});


	}

}
