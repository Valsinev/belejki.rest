package com.belejki.belejki.restful.dto;

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
    @NotBlank(message = "Ingredient must have name.")
    @Size(min = 2, max = 24, message = "Ingredient name must be between 2 and 24 characters.")
    private String ingredient;
    @NotBlank(message = "Ingredient must have quantity.")
    @Size(min = 2, max = 24, message = "Ingredient quantity must be between 2 and 24 characters.")
    private String quantity;

}
