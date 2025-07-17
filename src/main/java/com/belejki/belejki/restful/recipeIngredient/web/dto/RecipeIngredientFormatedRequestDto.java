package com.belejki.belejki.restful.recipeIngredient.web.dto;

import lombok.Data;


public class RecipeIngredientFormatedRequestDto {

	private Long id;
	private Long recipe;
	private String ingredient;
	private String quantity;

	@Override
	public String toString() {
		return "RecipeIngredientFormatedRequestDto{" +
				"id=" + id +
				", recipe=" + recipe +
				", ingredient='" + ingredient + '\'' +
				", quantity='" + quantity + '\'' +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Long getRecipe() {
		return recipe;
	}

	public void setRecipe(Long recipe) {
		this.recipe = recipe;
	}
}
