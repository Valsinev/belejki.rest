package com.belejki.belejki.restful.friendship.web;

import com.belejki.belejki.restful.friendship.service.FriendshipService;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipResponseDto;
import com.belejki.belejki.restful.shared.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class FriendshipController {

	private final FriendshipService friendshipService;
	private final AuthService authService;

	@Autowired
	public FriendshipController(FriendshipService friendshipService, AuthService authService) {
		this.friendshipService = friendshipService;
		this.authService = authService;
	}


	//region POST METHODS

	@PostMapping("/user/friendships")
	public ResponseEntity<FriendshipResponseDto> save(@Valid @RequestBody FriendshipDto friendshipDto,
	                                                  BindingResult bindingResult,
	                                                  Authentication authentication) {

		String username = authentication.getName();
		                                 //checks if you try to add yourself as friend
		if (bindingResult.hasErrors() || friendshipDto.getFriendUsername().toLowerCase().equals(username.toLowerCase())) {
			return ResponseEntity.badRequest().build();
		}

		FriendshipResponseDto saved = friendshipService.save(username, friendshipDto.getFriendUsername());
		return ResponseEntity.ok(saved);
	}

	//endregion


	//region GET METHODS

	@GetMapping("/admin/friendships")
	public ResponseEntity<Page<FriendshipResponseDto>> findAll(Authentication authentication, Pageable pageable) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Page<FriendshipResponseDto> all = friendshipService.findAll(pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/friendships")
	public ResponseEntity<Page<FriendshipResponseDto>> findAllUserFriendships(Pageable pageable,
	                                                                  Authentication authentication) {
		String username = authentication.getName();
		Page<FriendshipResponseDto> allByUser_Username = friendshipService.findAllByUser_Username(username, pageable);
		return ResponseEntity.ok(allByUser_Username);
	}

	@GetMapping("/user/friendships/first-name/{firstName}")
	public ResponseEntity<Page<FriendshipResponseDto>> findAllUserFriendshipsByFirstName(@PathVariable String firstName,
	                                                                             Pageable pageable,
	                                                                             Authentication authentication) {
		String username = authentication.getName();
		Page<FriendshipResponseDto> allUserFriendshipsByFirstName = friendshipService.findAllUserFriendshipsByFirstName(username, firstName, pageable);
		return ResponseEntity.ok(allUserFriendshipsByFirstName);
	}

	@GetMapping("/admin/friendships/{username}")
	public ResponseEntity<Page<FriendshipResponseDto>> findAllByUser_Username(@PathVariable String username,
	                                                                  Pageable pageable,
	                                                                  Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Page<FriendshipResponseDto> allByUserUsername = friendshipService.findAllByUser_Username(username, pageable);
		return ResponseEntity.ok(allByUserUsername);
	}


	//endregion


	//region DELETE METHODS

	@DeleteMapping("/user/friendships")
	public ResponseEntity<Void> delete(@RequestBody FriendshipDto friendshipDto, Authentication authentication) {
		String username = authentication.getName();
		friendshipService.deleteByFriendshipAndUser_Username(friendshipDto, username);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/friendships/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		friendshipService.deleteByIdAndUser_Username(id, username);
		return ResponseEntity.ok().build();
	}


	@DeleteMapping("/admin/friendships/friend/{friendUsername}")
	public ResponseEntity<Void> deleteAllByFriend_Username(@PathVariable String friendUsername,
	                                                                      Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		friendshipService.deleteAllByFriend_Username(friendUsername);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/friendships/user/{username}")
	public ResponseEntity<Page<Void>> deleteAllByUser_Username(@PathVariable String username,
	                                                           Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		friendshipService.deleteAllByUser_Username(username);
		return ResponseEntity.ok().build();
	}


	//endregion


}
