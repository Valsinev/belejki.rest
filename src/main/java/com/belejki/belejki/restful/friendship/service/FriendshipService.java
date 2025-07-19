package com.belejki.belejki.restful.friendship.service;

import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendshipService {
	FriendshipResponseDto save(String username, String  friendUsername);

	Page<FriendshipResponseDto> findAll(Pageable pageable);

	Page<FriendshipResponseDto> findAllByUser_Username(String username, Pageable pageable);

	Page<FriendshipResponseDto> findAllUserFriendshipsByFirstName(String username, String firstName, Pageable pageable);

	void deleteByFriendshipAndUser_Username(FriendshipDto friendshipDto, String username);

	void deleteByIdAndUser_Username(Long id, String username);

	void deleteAllByFriend_Username(String friendUsername);

	void deleteAllByUser_Username(String username);
}
