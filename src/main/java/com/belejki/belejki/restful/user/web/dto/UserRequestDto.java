package com.belejki.belejki.restful.user.web.dto;

import com.belejki.belejki.restful.authority.web.dto.AuthorityDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.recipe.web.dto.RecipeRequestDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderRequestDto;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemRequestDto;
import com.belejki.belejki.restful.wish.web.dto.WishRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private Long id;
    @NotBlank(message = "Username cannot be empty.")
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
    private String username;
    private String password;
    @NotBlank(message = "First name cannot be empty.")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;
    private boolean enabled;
    private LocalDate lastLogin;
    private boolean setForDeletion;
    private String locale;
    private Set<FriendshipDto> friendships;
    private Set<AuthorityDto> authorities;
    private List<ReminderRequestDto> reminders;
    private List<WishRequestDto> wishList;
    private Set<ShoppingItemRequestDto> shoppingItems;
    private List<RecipeRequestDto> recipes;
    private String confirmationToken;
    private LocalDateTime tokenExpiry;

}
