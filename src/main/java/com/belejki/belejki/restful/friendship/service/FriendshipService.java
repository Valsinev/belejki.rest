package com.belejki.belejki.restful.friendship.service;

import com.belejki.belejki.restful.friendship.web.dto.FriendshipDetailResponseDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {
	FriendshipDetailResponseDto save(String username, String  friendUsername);

	Page<FriendshipDetailResponseDto> findAll(Pageable pageable);

	Page<FriendshipDetailResponseDto> findAllByUser_Username(String username, Pageable pageable);

	Page<FriendshipDetailResponseDto> findAllUserFriendshipsByFirstName(String username, String firstName, Pageable pageable);

	void deleteByFriendshipAndUser_Username(FriendshipDto friendshipDto, String username);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByFriend_Username(String friendUsername);

	void deleteAllByUser_Username(String username);

	FriendshipDetailResponseDto findByFriend_Username(String username, String friendUsername);
}
