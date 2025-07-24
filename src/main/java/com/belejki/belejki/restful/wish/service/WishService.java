package com.belejki.belejki.restful.wish.service;

import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.wish.web.dto.WishDto;
import com.belejki.belejki.restful.wish.web.dto.WishPatchDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishService {
	WishDto save(@Valid WishDto dto, String username);

	Page<WishDto> findAllByUser_UsernameOrderByApproximatePriceDesc(String username, Pageable pageable);

	WishDto findByIdAndUsername(Long id, String username);

	Page<WishDto> findAllByUser_IdOrderByApproximatePriceDesc(Long id, Pageable pageable);

	Page<WishDto> findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(String description, String username, Pageable pageable);

	Page<WishDto> findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(Long price, String username, Pageable pageable);

	WishDto update(@Valid WishDto dto, String username);

	WishDto patchWish(@Valid WishPatchDto dto, String username);

	void deleteByIdAndUser_Username(Long id, String username);


	void deleteAllByUserId(Long id);

	void deleteAllByUser_Username(String username);

	Page<WishDto> findAllFriendWishes(String friendUsername, String username, Pageable pageable);
}
