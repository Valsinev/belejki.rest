package com.belejki.belejki.restful.friendship.web.dto;

import lombok.Data;

@Data
public class FriendshipResponseDto {
	private Long id;
	private String userUsername;
	private String friendUsername;
}
