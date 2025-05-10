package com.belejki.belejki.restful.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {

    private Long id;
    private Long userId;
    @NotBlank
    @Size(min = 2, max = 64, message = "Recipe name must be between 2 and 64 characters.")
    private String name;
    @Size(min = 10, max = 4000, message = "Instructions must be between 10 and 4000 characters.")
    @NotBlank(message = "Recipe must have instructions how to make.")
    private String howToMake;
    @NonNull
    @Valid
    private List<RecipeIngredientDto> ingredients;

}
