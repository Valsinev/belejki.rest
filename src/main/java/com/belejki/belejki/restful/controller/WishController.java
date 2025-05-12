package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.*;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.entity.Wish;
import com.belejki.belejki.restful.exception.WishNotFoundException;
import com.belejki.belejki.restful.mapper.WishMapper;
import com.belejki.belejki.restful.repository.WishRepository;
import com.belejki.belejki.restful.service.UserService;
import com.belejki.belejki.restful.service.WishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import static com.belejki.belejki.restful.controller.Utility.checkIfOwnerOrAdmin;

@RestController
@RequestMapping
public class WishController {

    private final WishRepository wishRepository;
    private final WishService wishService;
    private final UserService userService;
    private final WishMapper wishMapper;

    @Autowired
    public WishController(WishRepository wishRepository, WishService wishService, UserService userService, WishMapper wishMapper) {
        this.wishRepository = wishRepository;
        this.wishService = wishService;
        this.userService = userService;
        this.wishMapper = wishMapper;
    }

    //region POST METHODS

    @PostMapping("/user/wishlist")
    public Wish save(@RequestBody Wish wish) {
        return wishRepository.save(wish);
    }

    @PostMapping("/user/wishlist/user/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public WishDto saveForUser_Username(@Valid @RequestBody WishDto dto, @PathVariable String username, Authentication authentication) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        User user = userService.findByUsername(username);
        Wish entity = wishMapper.toEntity(dto, user);
        wishRepository.save(entity);
        return dto;
    }

    @PostMapping("/user/wishlist/user/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public WishDto saveForUser_Username(@Valid @RequestBody WishDto dto, @PathVariable Long id, Authentication authentication) {
        User user = userService.findById(id);
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Wish entity = wishMapper.toEntity(dto, user);
        wishRepository.save(entity);
        return dto;
    }

    //endregion

    //region GET METHODS
    @GetMapping("/user/wishlist/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public WishDto findById(@PathVariable Long id, Authentication authentication) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new WishNotFoundException("No wish item found for id: " + id));
        boolean access = checkIfOwnerOrAdmin(authentication, wish.getUser().getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        return wishMapper.toDto(wish, wish.getUser().getId());
    }

    @GetMapping("/user/wishlist/user/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Page<WishDto> findAllByUser_Id(@PathVariable Long id, Authentication authentication, Pageable pageable) {
        User user = userService.findById(id);
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Page<Wish> allByUserId = wishRepository.findAllByUser_Id(id, pageable);
        return allByUserId.map(wish -> wishMapper.toDto(wish, id));
    }

    @GetMapping("/user/wishlist/user/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Page<WishDto> findAllByUser_Username(@PathVariable String username, Authentication authentication, Pageable pageable) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Page<Wish> allByUserId = wishRepository.findAllByUser_Username(username, pageable);
        return allByUserId.map(wish -> wishMapper.toDto(wish, wish.getUser().getId()));
    }

    //endregion

    //region PUT METHODS

    @PutMapping("/user/wishlist/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public WishDto updateWishById(@PathVariable Long id,
                                          @Valid @RequestBody WishDto dto,
                                          Authentication authentication) {
        User user = userService.findById(dto.getUserId());
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can update this reminder.");
        }
        Wish byId = wishRepository.findById(id)
                .orElseThrow(() -> new WishNotFoundException("Wish not found for id: " + id));
        byId.setUser(user);
        Wish updated = wishService.update(byId, dto);
        return wishMapper.toDto(updated, updated.getUser().getId());
    }
    //endregion

    //region PATCH METHODS
    @PatchMapping("/user/wishlist/patch/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public WishDto patchWish(
            @PathVariable Long id,
            @Valid @RequestBody WishPatchDto dto,
            Authentication authentication) {
        User user = userService.findById(dto.getUserId());
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can update this reminder.");
        }

        Wish patchedReminder = wishService.patchWish(id, dto);
        return wishMapper.toDto(patchedReminder, patchedReminder.getUser().getId());
    }
    //endregion

    //region DELETE METHODS
    @DeleteMapping("/user/wishlist/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public WishDto deleteById(@PathVariable Long id, Authentication authentication) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new WishNotFoundException("No wish item found for id: " + id));
        boolean access = checkIfOwnerOrAdmin(authentication, wish.getUser().getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        wishRepository.delete(wish);
        return wishMapper.toDto(wish, wish.getUser().getId());
    }


    @DeleteMapping("/user/wishlist/user/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Page<WishDto> deleteAllByUser_Id(@PathVariable Long id, Authentication authentication, Pageable pageable) {
        User user = userService.findById(id);
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Page<Wish> allByUserId = wishRepository.findAllByUser_Id(id, pageable);
        wishRepository.deleteAll(allByUserId);
        return allByUserId.map(wish -> wishMapper.toDto(wish, id));
    }


    @DeleteMapping("/user/wishlist/user/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Page<WishDto> deleteAllByUser_Username(@PathVariable String username, Authentication authentication, Pageable pageable) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new wish item.");
        }
        Page<Wish> allByUserUsername = wishRepository.findAllByUser_Username(username, pageable);
        wishRepository.deleteAll(allByUserUsername);
        return allByUserUsername.map(wish -> wishMapper.toDto(wish, wish.getUser().getId()));
    }
    //endregion
}
