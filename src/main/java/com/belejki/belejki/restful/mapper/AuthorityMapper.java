package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.AuthorityAdminDto;
import com.belejki.belejki.restful.dto.AuthorityDto;
import com.belejki.belejki.restful.entity.Authority;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapper {

    // Static method for converting GrantedAuthority to AuthorityDto
    public static AuthorityAdminDto toDto(Authority authority) {
        if (authority == null) {
            return null;
        }
        AuthorityAdminDto dto = new AuthorityAdminDto();;
        dto.setId(authority.getId());
        dto.setRole(authority.getAuthority());
        return dto;
    }

    public AuthorityDto toDtoLong(Authority authority) {
        if (authority == null) {
            return null;
        }
        AuthorityDto dto = new AuthorityDto();
        dto.setId(authority.getId());
        dto.setUserId(authority.getUser().getId());
        dto.setUsername(authority.getUser().getUsername());
        dto.setAuthority(authority.getAuthority());
        return dto;
    }

    public Authority toEntity(AuthorityDto authorityDto) {
        Authority authority = new Authority();
        authority.setId(authorityDto.getId());
        authority.setAuthority(authorityDto.getAuthority());
        return authority;
    }
}
