package com.belejki.belejki.restful.shoppingItem.web;

import com.belejki.belejki.restful.shared.AuthService;
import com.belejki.belejki.restful.shoppingItem.service.ShoppingItemService;
import com.belejki.belejki.restful.shoppingItem.web.dto.ShoppingItemDto;
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
public class ShoppingItemController {

	private final ShoppingItemService shoppingItemService;
	private final AuthService authService;

	@Autowired
	public ShoppingItemController(ShoppingItemService shoppingItemService, AuthService authService) {
		this.shoppingItemService = shoppingItemService;
		this.authService = authService;
	}

	//region POST METHODS

	@PostMapping("/user/shopping-list")
	public ResponseEntity<ShoppingItemDto> save(@Valid @RequestBody ShoppingItemDto dto,
														BindingResult bindingResult,
	                                                    Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();
		ShoppingItemDto saved = shoppingItemService.save(dto, username);

		return ResponseEntity.ok(saved);
	}

	//endregion

	//region GET METHODS


	@GetMapping("/admin/shopping-list/user/id/{userId}")
	public ResponseEntity<Page<ShoppingItemDto>> findAllForUser_Id(@PathVariable Long userId, Authentication authentication, Pageable pageable) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ShoppingItemDto> all = shoppingItemService.findAllByUser_Id(userId, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/admin/shopping-list/user/{username}")
	public ResponseEntity<Page<ShoppingItemDto>> findAllForUser_Username(@PathVariable String username, Authentication authentication, Pageable pageable) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ShoppingItemDto> all = shoppingItemService.findAllByUser_Username(username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/shopping-list")
	public ResponseEntity<Page<ShoppingItemDto>> findAll(Authentication authentication, Pageable pageable) {
		String username = authentication.getName();
		Page<ShoppingItemDto> all = shoppingItemService.findAllByUser_Username(username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/shopping-list/{id}")
	ResponseEntity<ShoppingItemDto> findById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		ShoppingItemDto founded = shoppingItemService.findByIdAndUser_Username(id, username);
		return ResponseEntity.ok(founded);
	}


	//endregion

	//region DELETE METHODS
	@DeleteMapping("/user/shopping-list")
	public ResponseEntity<Void> delete(@RequestBody ShoppingItemDto shoppingItemDto, Authentication authentication) {
		String username = authentication.getName();
		shoppingItemService.deleteByIdAndUser_Username(shoppingItemDto.getId(), username);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/shopping-list/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		shoppingItemService.deleteByIdAndUser_Username(id, username);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/shopping-list/empty")
	public ResponseEntity<Void> deleteAllOwning(Authentication authentication) {
		String username = authentication.getName();
		shoppingItemService.deleteAllByUsername(username);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/shopping-list/{id}")
	public ResponseEntity<Void> deleteByIdForAdmin(@PathVariable Long id, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		shoppingItemService.deleteById(id);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/admin/shopping-list/empty/user/{username}")
	public ResponseEntity<Void> deleteAllOwning(@PathVariable String username, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		shoppingItemService.deleteAllByUsername(username);
		return ResponseEntity.ok(null);
	}
	//endregion
}

