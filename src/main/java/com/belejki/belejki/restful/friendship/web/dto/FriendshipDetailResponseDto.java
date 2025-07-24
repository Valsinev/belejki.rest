package com.belejki.belejki.restful.friendship.web.dto;

import lombok.Data;

@Data
public class FriendshipDetailResponseDto {

	private Long id;
	private Long friendId;
	private String friendUsername;
	private String friendFirstName;
	private String friendLastName;
}
