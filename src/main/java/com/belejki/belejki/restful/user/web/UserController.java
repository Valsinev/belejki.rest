package com.belejki.belejki.restful.user.web;

import com.belejki.belejki.restful.shared.AuthService;
import com.belejki.belejki.restful.user.service.UserService;
import com.belejki.belejki.restful.user.web.dto.UserDetailsResponseDto;
import com.belejki.belejki.restful.user.web.dto.UserDetailsShortDto;
import com.belejki.belejki.restful.user.web.dto.UserPatchRequestDto;
import com.belejki.belejki.restful.user.web.dto.UserRequestDto;
import com.belejki.belejki.restful.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Locale;


@RestController
public class UserController {

	private final UserService userService;
	private final AuthService authChecker;

	@Autowired
	public UserController(UserService userService, AuthService authChecker) {
		this.userService = userService;
		this.authChecker = authChecker;
	}


	//region POST METHODS

	@PostMapping("/user/users")
	public ResponseEntity<UserDetailsResponseDto> create(@Valid @RequestBody UserRequestDto user, BindingResult bindingResult, Locale locale) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		UserDetailsResponseDto saved = userService.createUser(user, locale);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/confirm")
	public ResponseEntity<UserDetailsResponseDto> confirmEmail(@RequestParam("token") String token) {
		UserRequestDto userOpt = userService.findByConfirmationToken(token);

		if (userOpt.getTokenExpiry().isBefore(LocalDateTime.now())) {
			return ResponseEntity.badRequest().build();
		}

		UserDetailsResponseDto saved = userService.enable(userOpt);

		return ResponseEntity.ok(saved);
	}


	//endregion

	//region PUT METHODS

	@PutMapping("/user/users/update/{id}")
	@PreAuthorize("hasRole('ADMIN') or #id == principal.id")
	public ResponseEntity<UserDetailsResponseDto> updateUserByUserId(@PathVariable Long id,
	                                                         @Valid @RequestBody UserRequestDto user) {
		UserDetailsResponseDto updated = userService.update(id, user);
		return ResponseEntity.ok(updated);
	}

	//endregion

	//region PATCH METHODS
	@PatchMapping("/user/users/patch/{id}")
	public ResponseEntity<UserDetailsResponseDto> patchUser(
			@PathVariable Long id,
			@RequestBody UserPatchRequestDto patchDto) {

		UserDetailsResponseDto updated = userService.patchUser(id, patchDto);

		return ResponseEntity.ok(updated);
	}

	//endregion

	//region GET METHODS
	@GetMapping("/admin/users/id/{id}")
	public ResponseEntity<UserDetailsResponseDto> findById(@PathVariable Long id,
	                                               Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		UserDetailsResponseDto byId = userService.findById(id);
		return ResponseEntity.ok(byId);
	}

	@GetMapping("/user/current")
	public ResponseEntity<UserDetailsShortDto> findCurrentUser(Authentication authentication) {
		String username = authentication.getName();
		UserDetailsShortDto userDetailsShortDto = userService.findCurrentUserBy_Username(username);
		return ResponseEntity.ok(userDetailsShortDto);
	}


	@GetMapping("/user")
	public ResponseEntity<UserDetailsResponseDto> findByUsername(Authentication authentication) {
		UserDetailsResponseDto user = userService.findByUsername(authentication.getName());
		return ResponseEntity.ok(user);
	}

	@GetMapping("/admin/users")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAll(Pageable pageable,
	                                                    Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> all = userService.findAll(pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/admin/users/not-logged/{months}")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllNotLoggedBefore(@PathVariable int months,
	                                                                   Pageable pageable,
	                                                                   Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> allNotLoggedBefore = userService.findAllNotLoggedBefore(months, pageable);
		return ResponseEntity.ok(allNotLoggedBefore);
	}

	@GetMapping("/admin/users/not-confirmed")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllNotConfirmed(Pageable pageable,
	                                                                Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> notConfirmed = userService.findAllByConfirmationTokenNotNull(pageable);
		return ResponseEntity.ok(notConfirmed);
	}

	@GetMapping("/admin/users/disabled")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllDisabled(Pageable pageable,
	                                                            Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> disabled = userService.findByEnabledFalse(pageable);
		return ResponseEntity.ok(disabled);
	}

	@GetMapping("/admin/users/set-for-deletion")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllBySetForDeletionTrue(Pageable pageable,
	                                                                        Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> allBySetForDeletionTrue = userService.findAllBySetForDeletionTrue(pageable);
		return ResponseEntity.ok(allBySetForDeletionTrue);
	}

	@GetMapping("/admin/users/{username}")
	public ResponseEntity<UserDetailsResponseDto> findAllByUsername(@PathVariable String username,
	                                                        Pageable pageable,
	                                                        Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		UserDetailsResponseDto allByUsername = userService.findByUsername(username);
		return ResponseEntity.ok(allByUsername);
	}

	@GetMapping("/admin/users/first-name/{firstName}")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllByFirstNameContaining(@PathVariable String firstName,
	                                                                         Pageable pageable,
	                                                                         Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> allByFirstNameContaining = userService.findAllByFirstNameContaining(firstName, pageable);
		return ResponseEntity.ok(allByFirstNameContaining);
	}


	@GetMapping("/admin/users/last-name/{lastName}")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllByLastNameContaining(@PathVariable String lastName,
	                                                                        Pageable pageable,
	                                                                        Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> allByLastNameContaining = userService.findAllByLastNameContaining(lastName, pageable);
		return ResponseEntity.ok(allByLastNameContaining);
	}

	@GetMapping("/admin/users/first-and-last-name")
	public ResponseEntity<Page<UserDetailsResponseDto>> findAllByFirstNameContainingAndLastNameContaining(@RequestParam String firstName,
	                                                                                              @RequestParam String lastName,
	                                                                                              Pageable pageable,
	                                                                                              Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<UserDetailsResponseDto> allByFirstNameContainingAndLastNameContaining = userService.findAllByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
		return ResponseEntity.ok(allByFirstNameContainingAndLastNameContaining);
	}

	//endregion

	//region DELETE METHODS

	@DeleteMapping("/admin/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@RequestBody User user, Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);

		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		userService.delete(user);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/users/id/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);
		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		userService.deleteById(id);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/users/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteByUsername(@PathVariable String username, Authentication authentication) {
		String loggedInUsername = authentication.getName();
		boolean isAdmin = authChecker.isAdmin(authentication);
		if (!loggedInUsername.equals(username) && !isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		userService.deleteByUsername(username);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/users/set-for-deletion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteAllByIsSetForDeletion(Authentication authentication, Pageable pageable) {
		boolean isAdmin = authChecker.isAdmin(authentication);
		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		userService.deleteAllByIsSetForDeletion(pageable);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/users/not-logged/{months}")
	public ResponseEntity<Void> deleteAllNotLoggedInYears(@PathVariable int months, Pageable pageable, Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);
		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		userService.deleteAllNotLoggedBefore(months, pageable);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/users/not-confirmed")
	public ResponseEntity<Void> deleteAllNotConfirmed(Pageable pageable, Authentication authentication) {
		boolean isAdmin = authChecker.isAdmin(authentication);
		if (!isAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		userService.deleteAllByConfirmationTokenNotNull(pageable);
		return ResponseEntity.ok(null);

	}


	//endregion


}
