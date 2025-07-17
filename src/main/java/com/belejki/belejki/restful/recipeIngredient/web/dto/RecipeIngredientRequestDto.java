package com.belejki.belejki.restful.recipeIngredient.web.dto;

import com.belejki.belejki.restful.ingredient.web.dto.IngredientRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientRequestDto {

    private Long id;
    private Long recipe;
    @NotNull
    private IngredientRequestDto ingredient;
    @NotBlank(message = "Ingredient must have quantity.")
    @Size(min = 2, max = 64, message = "Ingredient quantity must be between 2 and 64 characters.")
    private String quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IngredientRequestDto getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientRequestDto ingredient) {
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
