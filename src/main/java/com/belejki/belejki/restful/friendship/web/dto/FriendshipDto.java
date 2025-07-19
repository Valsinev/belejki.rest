package com.belejki.belejki.restful.friendship.web.dto;

import com.belejki.belejki.restful.user.web.dto.UserRegisterDto;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendshipDto {
    private Long id;

    @NotBlank(message = "Username cannot be empty.")
    @Pattern(regexp = "((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
    private String friendUsername;

}
