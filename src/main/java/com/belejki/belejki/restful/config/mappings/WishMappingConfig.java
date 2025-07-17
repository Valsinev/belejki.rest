package com.belejki.belejki.restful.config.mappings;

import com.belejki.belejki.restful.wish.domain.Wish;
import com.belejki.belejki.restful.wish.web.dto.WishRequestDto;
import com.belejki.belejki.restful.wish.web.dto.WishResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishMappingConfig {

	@Autowired
	public void configureWishMappings(ModelMapper modelMapper) {
		modelMapper.createTypeMap(Wish.class, WishRequestDto.class)
				.addMappings(mapper -> {

					mapper.map(Wish::getId, WishRequestDto::setId);
					mapper.map(Wish::getDescription, WishRequestDto::setDescription);
					mapper.map(Wish::getApproximatePrice, WishRequestDto::setApproximatePrice);
					mapper.map(Wish::getLink, WishRequestDto::setLink);
				});

		modelMapper.createTypeMap(WishRequestDto.class, Wish.class)
				.addMappings(mapper -> {
					mapper.skip(Wish::setUser);

					mapper.map(WishRequestDto::getId, Wish::setId);
					mapper.map(WishRequestDto::getDescription, Wish::setDescription);
					mapper.map(WishRequestDto::getApproximatePrice, Wish::setApproximatePrice);
					mapper.map(WishRequestDto::getLink, Wish::setLink);
				});

		modelMapper.createTypeMap(Wish.class, WishResponseDto.class)
				.addMappings(mapper -> {

					mapper.map(Wish::getId, WishResponseDto::setId);
					mapper.map(Wish::getDescription, WishResponseDto::setDescription);
					mapper.map(Wish::getApproximatePrice, WishResponseDto::setApproximatePrice);
					mapper.map(Wish::getLink, WishResponseDto::setLink);
					mapper.map(wish -> wish.getUser().getUsername(), WishResponseDto::setUser);
				});
	}

}
