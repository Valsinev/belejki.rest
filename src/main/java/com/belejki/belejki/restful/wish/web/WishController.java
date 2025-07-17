package com.belejki.belejki.restful.wish.web;

import com.belejki.belejki.restful.shared.AuthService;
import com.belejki.belejki.restful.wish.service.WishService;
import com.belejki.belejki.restful.wish.web.dto.WishRequestDto;
import com.belejki.belejki.restful.wish.web.dto.WishResponseDto;
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
	public ResponseEntity<WishResponseDto> save(@Valid @RequestBody WishRequestDto dto, BindingResult bindingResult, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();

		WishResponseDto saved = wishService.save(dto, username);
		return ResponseEntity.ok(saved);
	}

	//endregion

	//region GET METHODS
	@GetMapping("/user/wishlist")
	public ResponseEntity<Page<WishResponseDto>> findAllUserWishes(Authentication authentication, Pageable pageable) {
		String username = authentication.getName();
		Page<WishResponseDto> all = wishService.findAllByUser_UsernameOrderByApproximatePriceDesc(username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/wishlist/{id}")
	public ResponseEntity<WishResponseDto> findById(@PathVariable Long id, Authentication authentication) {

		String username = authentication.getName();
		WishResponseDto wishDto = wishService.findByIdAndUsername(id, username);
		return ResponseEntity.ok(wishDto);
	}

	@GetMapping("/admin/wishlist/user/id/{id}")
	public ResponseEntity<Page<WishResponseDto>> findAllByUser_Id(@PathVariable Long id, Pageable pageable, Authentication authentication) {
		boolean ownerOrAdmin = authChecker.checkIfOwnerOrAdminByUser_Id(authentication, id);
		if (!ownerOrAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<WishResponseDto> allByUserId = wishService.findAllByUser_IdOrderByApproximatePriceDesc(id, pageable);
		return ResponseEntity.ok(allByUserId);
	}

	@GetMapping("/user/wishlist/user/{username}")
	public ResponseEntity<Page<WishResponseDto>> findAllByUser_Username(@PathVariable String username, Pageable pageable, Authentication authentication) {
		boolean ownerOrAdmin = authChecker.checkIfOwnerOrAdminByUsername(authentication, username);
		if (!ownerOrAdmin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<WishResponseDto> allByUserId = wishService.findAllByUser_UsernameOrderByApproximatePriceDesc(username, pageable);
		return ResponseEntity.ok(allByUserId);
	}

	@GetMapping("/user/wishlist/description/{description}")
	public ResponseEntity<Page<WishResponseDto>> findAllUserWishesByDescriptionContaining(@PathVariable String description,
	                                                                                     Authentication authentication,
	                                                                                     Pageable pageable) {
		String username = authentication.getName();
		Page<WishResponseDto> all = wishService.findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(description, username, pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/user/wishlist/price/{price}")
	public ResponseEntity<Page<WishResponseDto>> findAllUserWishesByPriceBellow(@PathVariable Long price, Authentication authentication, Pageable pageable) {
		String username = authentication.getName();
		Page<WishResponseDto> all = wishService.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return ResponseEntity.ok(all);
	}


	@GetMapping("/user/wishlist/by-price-and-username")
	public ResponseEntity<Page<WishResponseDto>> findAllWishesByPriceBellowAndUser_Username(@RequestParam Long price, @Email @RequestParam String username, Pageable pageable) {
		Page<WishResponseDto> all = wishService.findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(price, username, pageable);
		return ResponseEntity.ok(all);
	}


	//endregion

	//region PUT METHODS

	@PutMapping("/user/wishlist/update")
	public ResponseEntity<WishResponseDto> updateWishById(@Valid @RequestBody WishRequestDto dto,
	                                                     BindingResult bindingResult,
	                                                     Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();
		WishResponseDto wish = wishService.update(dto, username);

		return ResponseEntity.ok(wish);
	}
	//endregion

	//region PATCH METHODS
	@PatchMapping("/user/wishlist/patch")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
	public ResponseEntity<WishResponseDto> patchWish(@Valid @RequestBody WishRequestDto dto,
	                                                Authentication authentication) {

		String username = authentication.getName();

		WishResponseDto patchedWish = wishService.patchWish(dto, username);
		return ResponseEntity.ok(patchedWish);
	}
	//endregion

	//region DELETE METHODS
	@DeleteMapping("/user/wishlist/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		wishService.delete(id, username);
		return ResponseEntity.ok(null);
	}


	@DeleteMapping("/admin/wishlist/user/id/{id}")
	public ResponseEntity<Void> deleteAllByUser_Id(@PathVariable Long id, Authentication authentication) {
		boolean admin = authChecker.isAdmin(authentication);

		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		wishService.deleteAllByUserId(id);
		return ResponseEntity.ok(null);
	}


	@DeleteMapping("/admin/wishlist/user/{username}")
	public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable String username, Authentication authentication) {
		boolean admin = authChecker.isAdmin(authentication);

		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		wishService.deleteAllByUser_Username(username);
		return ResponseEntity.ok(null);
	}
	//endregion
}
