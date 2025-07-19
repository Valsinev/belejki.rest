package com.belejki.belejki.restful.recipe.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class FriendRecipesByIngredientsAndUsernameDto {

	@NotBlank(message = "Username cannot be empty.")
	@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
	private final String friendUsername;
	@NotNull
	private final List<String> ingredients;
}
