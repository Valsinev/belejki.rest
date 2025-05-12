package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.WishDto;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.entity.Wish;
import org.springframework.stereotype.Component;

@Component
public class WishMapper {
    public WishDto toDto(Wish wish, Long userId) {
        WishDto dto = new WishDto();
        dto.setId(wish.getId());
        dto.setUserId(userId);
        dto.setDescription(wish.getDescription());
        dto.setApproximatePrice(wish.getApproximatePrice());
        dto.setLink(wish.getLink());
        return dto;
    }

    public Wish toEntity(WishDto dto, User user) {
        Wish wish = new Wish();
        wish.setId(dto.getId());
        wish.setUser(user);
        wish.setDescription(dto.getDescription());
        wish.setApproximatePrice(dto.getApproximatePrice());
        wish.setLink(dto.getLink());
        return wish;
    }
}
