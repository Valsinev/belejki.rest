package com.belejki.belejki.restful.wish.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishRequestDto extends WishResponseDto {
    private Long id;
    @NotNull
    @Size(min = 2, max = 64, message = "Wish description must be between 2 and 64 characters.")
    private String description;
    @NotNull
    @Min(value = 1, message = "The price must be at least 1.")
    @Max(value = Long.MAX_VALUE, message = "The price is too large.")
    private Double approximatePrice;
    @NotNull
    @Pattern(regexp = "https?:\\/\\/[\\w\\-\\.~:\\/?#\\[\\]@!$&'()*+,;=%]+", message = "{wish.invalid.link}")
    private String link;

}
