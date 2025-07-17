package com.belejki.belejki.restful.recipeIngredient.web.dto;

import lombok.Data;

@Data
public class RecipeIngredientResponseDto {

	private Long id;
	private Long recipe;
	private String ingredient;
	private String quantity;
}
