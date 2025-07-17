package com.belejki.belejki.restful.config.mappings;

import com.belejki.belejki.restful.shoppingItem.domain.ShoppingItem;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemRequestDto;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingItemMappingConfig {
	@Autowired
	public void configureShoppingItemMappings(ModelMapper modelMapper) {
		modelMapper.createTypeMap(ShoppingItem.class, ShoppingItemRequestDto.class)
				.addMappings(mapper -> {

					mapper.map(ShoppingItem::getId, ShoppingItemRequestDto::setId);
					mapper.map(ShoppingItem::getName, ShoppingItemRequestDto::setName);
					mapper.map(ShoppingItem::getColor, ShoppingItemRequestDto::setColor);
					mapper.map(ShoppingItem::getPrice, ShoppingItemRequestDto::setPrice);
				});

		modelMapper.createTypeMap(ShoppingItem.class, ShoppingItemResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(ShoppingItem::getId, ShoppingItemResponseDto::setId);
					mapper.map(item -> item.getUser().getUsername(), ShoppingItemResponseDto::setUser);
					mapper.map(ShoppingItem::getName, ShoppingItemResponseDto::setName);
					mapper.map(ShoppingItem::getColor, ShoppingItemResponseDto::setColor);
					mapper.map(ShoppingItem::getPrice, ShoppingItemResponseDto::setPrice);
				});

		modelMapper.createTypeMap(ShoppingItemRequestDto.class, ShoppingItem.class)
				.addMappings(mapper -> {

					mapper.map(ShoppingItemRequestDto::getId, ShoppingItem::setId);
					mapper.map(ShoppingItemRequestDto::getName, ShoppingItem::setName);
					mapper.map(ShoppingItemRequestDto::getColor, ShoppingItem::setColor);
					mapper.map(ShoppingItemRequestDto::getPrice, ShoppingItem::setPrice);
				});
	}
}
