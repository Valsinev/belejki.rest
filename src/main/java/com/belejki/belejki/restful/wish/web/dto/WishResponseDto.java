package com.belejki.belejki.restful.wish.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WishResponseDto {
	private Long id;
	private String user;  //username (email)
	private String description;
	private Double approximatePrice;
	private String link;
}
