package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.dto.WishDto;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipMapper {


    public FriendshipDto toDto(Friendship friendship) {
        FriendshipDto dto = new FriendshipDto();
        dto.setId(friendship.getId());
        dto.setUsername(friendship.getUser().getUsername());
        dto.setFriendName(friendship.getFriend().getUsername());
        dto.setFirstName(friendship.getFriend().getFirstName());
        dto.setLastName(friendship.getFriend().getLastName());

        return dto;
    }
}
