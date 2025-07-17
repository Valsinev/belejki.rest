package com.belejki.belejki.restful.authority.web.dto;

import com.belejki.belejki.restful.authority.domain.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {

    private Long id;
    @NotBlank
    private String user;
    @NotNull
    private UserRoles authority;
}
