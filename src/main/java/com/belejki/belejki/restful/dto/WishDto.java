package com.belejki.belejki.restful.dto;

import com.belejki.belejki.restful.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishDto {
    private Long id;
    private Long userId;
    @NotNull
    @Size(min = 2, max = 24, message = "Wish description must be between 2 and 24 characters.")
    private String description;
    @NotNull
    @Min(value = 1, message = "The price must be at least 1.")
    @Max(value = Long.MAX_VALUE, message = "The price is too large.")
    private Double approximatePrice;
    @NotNull
    @Size(min = 2, max = 1024, message = "Wish link must be between 2 and 1024 characters.")
    private String link;

}
