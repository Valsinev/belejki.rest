package com.belejki.belejki.restful.wish.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WishPatchDto {

	@NotNull
	private Long id;
	private String userUsername;
	private String description;
	private Double approximatePrice;
	private String link;
}
