package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.ShoppingItemDto;
import com.belejki.belejki.restful.entity.ShoppingItem;
import com.belejki.belejki.restful.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ShoppingItemMapper {
    public ShoppingItemDto toDto(ShoppingItem shoppingItem, Long userId) {
        ShoppingItemDto dto = new ShoppingItemDto();
        dto.setId(shoppingItem.getId());
        dto.setUserId(userId);
        dto.setName(shoppingItem.getName());
        dto.setColor(shoppingItem.getColor());
        return dto;
    }

    public ShoppingItem toEntity(ShoppingItemDto dto, User user) {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(dto.getId());
        shoppingItem.setUser(user);
        shoppingItem.setName(dto.getName());
        shoppingItem.setColor(dto.getColor());
        return shoppingItem;
    }
}
