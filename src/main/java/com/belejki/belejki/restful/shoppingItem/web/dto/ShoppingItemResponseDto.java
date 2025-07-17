package com.belejki.belejki.restful.shoppingItem.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingItemResponseDto {
	private Long id;
	private String user;
	private String name;
	private String color;
	private BigDecimal price;
}
