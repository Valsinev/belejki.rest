package com.belejki.belejki.restful.dto;

import com.belejki.belejki.restful.entity.Wish;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FriendshipDto {
    private String username;
    private String friendName;
    private List<Wish> friendWishlist;

    public FriendshipDto(String username, String friendName) {
        this.username = username;
        this.friendName = friendName;
    }

    public FriendshipDto(String username, String friendName, List<Wish> wishlist) {
        this.friendName = friendName;
        this.username = username;
        this.friendWishlist = wishlist;
    }
}
