package com.belejki.belejki.restful;

import com.belejki.belejki.restful.ingredient.domain.Ingredient;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import com.belejki.belejki.restful.ingredient.web.dto.IngredientResponseDto;
import com.belejki.belejki.restful.recipeIngredient.domain.RecipeIngredient;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientFormatedRequestDto;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientRequestDto;
import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CRunner implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {

		ModelMapper modelMapper = new ModelMapper();

		RecipeIngredientRequestDto requestDto = new RecipeIngredientRequestDto();
		requestDto.setQuantity("500g");

		IngredientRequestDto ingredientDto = new IngredientRequestDto();
		ingredientDto.setName("tomatoes");

		requestDto.setIngredient(ingredientDto);

		RecipeIngredientFormatedRequestDto formattedDto =
				modelMapper.map(requestDto, RecipeIngredientFormatedRequestDto.class);

		System.out.println(formattedDto);

	}
}
