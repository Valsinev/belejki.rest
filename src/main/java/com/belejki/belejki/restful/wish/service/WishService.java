package com.belejki.belejki.restful.wish.service;

import com.belejki.belejki.restful.wish.web.dto.WishRequestDto;
import com.belejki.belejki.restful.wish.web.dto.WishResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishService {
	WishResponseDto save(@Valid WishRequestDto dto, String username);

	Page<WishResponseDto> findAllByUser_UsernameOrderByApproximatePriceDesc(String username, Pageable pageable);

	WishResponseDto findByIdAndUsername(Long id, String username);

	Page<WishResponseDto> findAllByUser_IdOrderByApproximatePriceDesc(Long id, Pageable pageable);

	Page<WishResponseDto> findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(String description, String username, Pageable pageable);

	Page<WishResponseDto> findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(Long price, String username, Pageable pageable);

	WishResponseDto update(@Valid WishRequestDto dto, String username);

	WishResponseDto patchWish(@Valid WishRequestDto dto, String username);

	void delete(Long id, String username);


	void deleteAllByUserId(Long id);

	void deleteAllByUser_Username(String username);

}
