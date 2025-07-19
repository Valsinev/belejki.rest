package com.belejki.belejki.restful.authority.web.dto;

import com.belejki.belejki.restful.authority.domain.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {

    private Long id;
    @NotBlank
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
    private String userUsername;
    @NotNull
    private UserRoles authority;
}
