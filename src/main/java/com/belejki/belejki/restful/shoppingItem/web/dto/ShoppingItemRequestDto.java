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
public class ShoppingItemRequestDto extends ShoppingItemResponseDto {
    private Long id;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item name must be between 2 and 24 characters.")
    private String name;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item color must be between 2 and 24 characters.")
    private String color;

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal price;
}
