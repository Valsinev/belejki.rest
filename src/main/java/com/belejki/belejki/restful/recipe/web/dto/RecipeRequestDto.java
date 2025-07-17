package com.belejki.belejki.restful.recipe.web.dto;

import com.belejki.belejki.restful.recipeIngredient.web.dto.RecipeIngredientRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RecipeRequestDto {

    private Long id;
    @NotBlank
    @Size(min = 2, max = 64, message = "Recipe name must be between 2 and 64 characters.")
    private String name;
    @Size(min = 10, max = 4000, message = "Instructions must be between 10 and 4000 characters.")
    @NotBlank(message = "Recipe must have instructions how to make.")
    private String instructions;
    @Pattern(regexp = "https?:\\/\\/[\\w\\-\\.~:\\/?#\\[\\]@!$&'()*+,;=%]+", message = "{recipe.invalid.link}")
    private String videoLink;
    @NotNull
    @Valid
    private List<RecipeIngredientRequestDto> ingredients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RecipeIngredientRequestDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientRequestDto> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
