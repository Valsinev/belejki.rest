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

    private final WishMapper wishMapper;

    @Autowired
    public FriendshipMapper(WishMapper wishMapper) {
        this.wishMapper = wishMapper;
    }

    public FriendshipDto toDto(Friendship friendship) {
        FriendshipDto dto = new FriendshipDto();
        dto.setId(friendship.getId());
        dto.setUsername(friendship.getUser().getUsername());
        dto.setFriendName(friendship.getFriend().getUsername());
        List<Wish> wishList = friendship.getFriend().getWishList();
        List<WishDto> wishListDto = wishList.stream().map(wish -> wishMapper.toDto(wish, wish.getUser().getId())).toList();
        dto.setFriendWishlist(wishListDto);

        return dto;
    }
}
