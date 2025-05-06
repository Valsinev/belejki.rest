package com.belejki.belejki.restful.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {

    private Long id;
    @NotBlank
    @NonNull
    private Long userId;
    private String username;
    @NotBlank
    private String authority;
}
