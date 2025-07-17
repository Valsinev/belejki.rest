package com.belejki.belejki.restful.recipe.web.dto;

import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResponseDto {


	private Long id;
	private String user;
	private String name;
	private String instructions;
	private String videoLink;
	private List<RecipeIngredientResponseDto> ingredients;

}
