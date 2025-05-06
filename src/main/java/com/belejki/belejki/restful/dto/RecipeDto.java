package com.belejki.belejki.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {

    private String name;
    private String howToMake;
    private List<RecipeIngredientDto> ingredients;

}
