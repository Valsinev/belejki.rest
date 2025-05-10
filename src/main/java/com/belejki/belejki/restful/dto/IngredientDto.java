package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IngredientDto {
    private Long id;
    private List<Long> recipeIngredientIds;
    @NotBlank
    @Size(min = 2, max = 24, message = "Ingredient must be between 2 and 24 characters.")
    private String name;

}
