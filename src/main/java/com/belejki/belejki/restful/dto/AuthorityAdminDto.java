package com.belejki.belejki.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityAdminDto {
    private Long id;
    private String role;
}
