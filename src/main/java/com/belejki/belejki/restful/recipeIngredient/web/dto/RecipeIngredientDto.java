package com.belejki.belejki.restful.recipeIngredient.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {

    private Long id;
    private Long recipeId;
    @NotBlank
    private String ingredientName;
    @NotBlank(message = "Ingredient must have quantity.")
    @Size(min = 2, max = 64, message = "Ingredient quantity must be between 2 and 64 characters.")
    private String quantity;

}
