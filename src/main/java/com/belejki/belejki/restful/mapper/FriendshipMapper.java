package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.entity.Friendship;
import org.springframework.stereotype.Component;

@Component
public class FriendshipMapper {

    public FriendshipDto toDto(Friendship friendship) {
        FriendshipDto dto = new FriendshipDto();
        dto.setId(friendship.getId());
        dto.setUsername(friendship.getUser().getUsername());
        dto.setFriendName(friendship.getFriend().getUsername());
        dto.setFriendWishlist(friendship.getFriend().getWishList());
        return dto;
    }
}
