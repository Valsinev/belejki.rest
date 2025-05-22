package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.UserAdminDto;
import com.belejki.belejki.restful.dto.UserDto;
import com.belejki.belejki.restful.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEnabled(user.isEnabled());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }

    // Mapping method for User to UserAdminDto
    public UserAdminDto toAdminDto(User user) {
        UserAdminDto dto = new UserAdminDto();

        // Basic field mappings
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEnabled(user.isEnabled());
        dto.setLastLogin(user.getLastLogin());
        dto.setConfirmationToken(user.getConfirmationToken());

        // Map authorities using the entitiesMapper helper
        dto.setAuthorities(MapperUtility.entitiesMapper(user.getRoles(),AuthorityMapper::toDto));

        // Return the fully mapped UserAdminDto
        return dto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEnabled(dto.isEnabled());
        user.setLastLogin(dto.getLastLogin());
        return user;
    }

}
