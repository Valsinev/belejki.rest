package com.belejki.belejki.restful.recipe.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FriendRecipesByUsernameDto {

	@NotBlank(message = "Username cannot be empty.")
	@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
	private final String friendUsername;
	@NotBlank
	private final String recipeName;
}
