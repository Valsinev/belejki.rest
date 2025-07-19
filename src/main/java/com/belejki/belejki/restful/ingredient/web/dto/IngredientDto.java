package com.belejki.belejki.restful.ingredient.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class IngredientDto {

	private Long id;
	@NotBlank
	private String name;
}
