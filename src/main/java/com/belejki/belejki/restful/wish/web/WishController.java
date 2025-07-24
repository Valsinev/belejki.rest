package com.belejki.belejki.restful.wish.web;

import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.shared.AuthService;
import com.belejki.belejki.restful.wish.service.WishService;
import com.belejki.belejki.restful.wish.web.dto.WishDto;
import com.belejki.belejki.restful.wish.web.dto.WishPatchDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class WishController {

	private final WishService wishService;
	private final AuthService authChecker;

	@Autowired
	public WishController(WishService wishService, AuthService authChecker) {
		this.wishService = wishService;
		this.authChecker = authChecker;
	}

	//region POST METHODS

	@PostMapping("/user/wishlist")
	public ResponseEntity<WishDto> save(@Valid @RequestBody WishDto dto, BindingResult bindingResult, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();

		WishDto saved = wishService.save(dto, username);
		return ResponseEntity.ok(saved);
	}

	//endregion

	//region GET METHODS
	@GetMapping("/user/wishlist")
	public ResponseEntity<Page<WishDto>> findAllUserWishes(Authentication authentication, Pageable pageable) {
		String username = authentication.getName();
		Page<WishDto> all = wishService.findAllByUser_UsernameOrderByApproximatePriceDesc(username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/wishlist/{id}")
	public ResponseEntity<WishDto> findById(@PathVariable Long id, Authentication authentication) {

		String username = authentication.getName();
		WishDto wishDto = wishService.findByIdAndUsername(id, username);
		return ResponseEntity.ok(wishDto);
	}

	@GetMapping("/admin/wishlist/user/id/{id}")
	public ResponseEntity<Page<WishDto>> findAllByUser_Id(@PathVariable Long id, Pageable pageable, Authentication authentication) {
		boolean ownerOrAdmin = authChecker.checkIfOwnerOrAdminByUser_Id(authentication, id);
		if (!ownerOrAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<WishDto> allByUserId = wishService.findAllByUser_IdOrderByApproximatePriceDesc(id, pageable);
		return ResponseEntity.ok(allByUserId);
	}

	@GetMapping("/user/friend/wishlist/{friendUsername}")
	public ResponseEntity<Page<WishDto>> findAllFriendWishes(@Email @PathVariable String friendUsername,
	                                                         Pageable pageable,
	                                                         Authentication authentication) {

		String username = authentication.getName();

		Page<WishDto> allByUserId = wishService.findAllFriendWishes(friendUsername, username, pageable);
		return ResponseEntity.ok(allByUserId);
	}

	@GetMapping("/user/wishlist/description/{description}")
	public ResponseEntity<Page<WishDto>> findAllUserWishesByDescriptionContaining(@PathVariable String description,
	                                                                                     Authentication authentication,
	                                                                                     Pageable pageable) {
		String username = authentication.getName();
		Page<WishDto> all = wishService.findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(description, username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/wishlist/price/{price}")
	public ResponseEntity<Page<WishDto>> findAllUserWishesByPriceBellow(@PathVariable Long price, Authentication authentication, Pageable pageable) {
		String username = authentication.getName();
		Page<WishDto> all = wishService.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return ResponseEntity.ok(all);
	}


	@GetMapping("/user/wishlist/by-price-and-username")
	public ResponseEntity<Page<WishDto>> findAllWishesByPriceBellowAndUser_Username(@RequestParam Long price, @Email @RequestParam String username, Pageable pageable) {
		Page<WishDto> all = wishService.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return ResponseEntity.ok(all);
	}


	//endregion

	//region PUT METHODS

	@PutMapping("/user/wishlist")
	public ResponseEntity<WishDto> updateWishById(@Valid @RequestBody WishDto dto,
	                                                     BindingResult bindingResult,
	                                                     Authentication authentication) {

		if (bindingResult.hasErrors() || dto.getId() == null) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();
		WishDto wish = wishService.update(dto, username);

		return ResponseEntity.ok(wish);
	}
	//endregion

	//region PATCH METHODS
	@PatchMapping("/user/wishlist")
	public ResponseEntity<WishDto> patchWish(@Valid @RequestBody WishPatchDto dto,
													BindingResult bindingResult,
	                                                Authentication authentication) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();

		WishDto patchedWish = wishService.patchWish(dto, username);
		return ResponseEntity.ok(patchedWish);
	}
	//endregion

	//region DELETE METHODS
	@DeleteMapping("/user/wishlist/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		wishService.deleteByIdAndUser_Username(id, username);
		return ResponseEntity.ok().build();
	}


	@DeleteMapping("/admin/wishlist/user/id/{id}")
	public ResponseEntity<Void> deleteAllByUser_Id(@PathVariable Long id, Authentication authentication) {
		boolean admin = authChecker.isAdmin(authentication);

		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		wishService.deleteAllByUserId(id);
		return ResponseEntity.ok().build();
	}


	@DeleteMapping("/admin/wishlist/user/{username}")
	public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable String username, Authentication authentication) {
		boolean admin = authChecker.isAdmin(authentication);

		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		wishService.deleteAllByUser_Username(username);
		return ResponseEntity.ok().build();
	}
	//endregion
}
