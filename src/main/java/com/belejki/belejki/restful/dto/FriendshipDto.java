package com.belejki.belejki.restful.dto;

import com.belejki.belejki.restful.entity.Wish;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FriendshipDto {
    private Long id;
    @NotBlank
    @Email(message = "Invalid format for email address.")
    private String username;
    @NotBlank
    @Email(message = "Invalid format for email address.")
    private String friendName;
    private List<WishDto> friendWishlist;

    public FriendshipDto(String username, String friendName) {
        this.username = username;
        this.friendName = friendName;
    }

    public FriendshipDto(String username, String friendName, List<WishDto> wishlist) {
        this.friendName = friendName;
        this.username = username;
        this.friendWishlist = wishlist;
    }
}
