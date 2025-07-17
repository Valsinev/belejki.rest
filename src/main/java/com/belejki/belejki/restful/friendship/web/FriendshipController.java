package com.belejki.belejki.restful.friendship.web;

import com.belejki.belejki.restful.friendship.service.FriendshipService;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.shared.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
	public ResponseEntity<FriendshipDto> save(@Valid @RequestBody FriendshipDto friendshipDto,
	                                          Authentication authentication) {
		String username = authentication.getName();

		FriendshipDto saved = friendshipService.save(username, friendshipDto);
		return ResponseEntity.ok(saved);
	}

	//endregion


	//region GET METHODS

	@GetMapping("/admin/friendships")
	public ResponseEntity<Page<FriendshipDto>> findAll(Authentication authentication, Pageable pageable) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Page<FriendshipDto> all = friendshipService.findAll(pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/friendships")
	public ResponseEntity<Page<FriendshipDto>> findAllUserFriendships(Pageable pageable,
	                                                                  Authentication authentication) {
		String username = authentication.getName();
		Page<FriendshipDto> allByUser_Username = friendshipService.findAllByUser_Username(username, pageable);
		return ResponseEntity.ok(allByUser_Username);
	}

	@GetMapping("/user/friendships/first-name/{firstName}")
	public ResponseEntity<Page<FriendshipDto>> findAllUserFriendshipsByFirstName(@PathVariable String firstName,
	                                                                             Pageable pageable,
	                                                                             Authentication authentication) {
		String username = authentication.getName();
		Page<FriendshipDto> allUserFriendshipsByFirstName = friendshipService.findAllUserFriendshipsByFirstName(username, firstName, pageable);
		return ResponseEntity.ok(allUserFriendshipsByFirstName);
	}

	@GetMapping("/admin/friendships/{username}")
	public ResponseEntity<Page<FriendshipDto>> findAllByUser_Username(@PathVariable String username,
	                                                                  Pageable pageable,
	                                                                  Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Page<FriendshipDto> allByUserUsername = friendshipService.findAllByUser_Username(username, pageable);
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
	public ResponseEntity<FriendshipDto> deleteById(@PathVariable Long id, Authentication authentication) {
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
	public ResponseEntity<Page<FriendshipDto>> deleteAllByUser_Username(@PathVariable String username,
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
