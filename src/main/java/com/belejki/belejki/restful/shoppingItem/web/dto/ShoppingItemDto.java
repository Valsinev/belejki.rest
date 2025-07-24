package com.belejki.belejki.restful.shoppingItem.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ShoppingItemDto {
    private Long id;
    private String userUsername;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item name must be between 2 and 24 characters.")
    private String name;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item color must be between 2 and 24 characters.")
    private String color;

    @DecimalMin(value = "0")
    private BigDecimal price;
}
