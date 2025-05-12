package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WishPatchDto {
    private Long id;
    private Long userId;
    @Size(min = 2, max = 24, message = "Wish description must be between 2 and 24 characters.")
    private String description;
    private Double approximatePrice;
    @Size(min = 2, max = 1024, message = "Wish link must be between 2 and 1024 characters.")
    private String link;

}
