package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingItemDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item name must be between 2 and 24 characters.")
    private String name;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item color must be between 2 and 24 characters.")
    private String color;
}
