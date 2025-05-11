package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.ShoppingItemDto;
import com.belejki.belejki.restful.entity.ShoppingItem;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.ShoppingItemNotFoundException;
import com.belejki.belejki.restful.mapper.ShoppingItemMapper;
import com.belejki.belejki.restful.repository.ShoppingItemRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import com.belejki.belejki.restful.service.UserService;
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
public class ShoppingItemController {

    private final ShoppingItemRepository shoppingItemRepository;
    private final ShoppingItemMapper shoppingItemMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public ShoppingItemController(ShoppingItemRepository shoppingItemRepository, ShoppingItemMapper shoppingItemMapper, UserRepository userRepository, UserService userService) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingItemMapper = shoppingItemMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    //region POST METHODS

    @PostMapping("/user/shopping-list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    ShoppingItemDto save(@Valid @RequestBody ShoppingItemDto dto, Authentication authentication) {
        User user = userService.findById(dto.getUserId());
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new shopping item.");
        }
        ShoppingItem entity = shoppingItemMapper.toEntity(dto, user);
        shoppingItemRepository.save(entity);
        return dto;
    }

    //endregion

    //region GET METHODS


    @GetMapping("/user/shopping-list/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    ShoppingItemDto findById(@PathVariable Long id, Authentication authentication) {
        ShoppingItem founded = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingItemNotFoundException("No shopping item found for id: " + id));
        boolean access = checkIfOwnerOrAdmin(authentication, founded.getUser().getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new shopping item.");
        }
        return shoppingItemMapper.toDto(founded, id);
    }

    @GetMapping("/user/shopping-list/user/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    Page<ShoppingItemDto> findAll(@PathVariable String username, Pageable pageable, Authentication authentication) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new shopping item.");
        }
        Page<ShoppingItem> all = shoppingItemRepository.findAllByUser_Username(username, pageable);
        return all.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
    }

    @GetMapping("/user/shopping-list/user/id/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    Page<ShoppingItemDto> findAll(@PathVariable Long userId, Pageable pageable, Authentication authentication) {
        User user = userService.findById(userId);
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new shopping item.");
        }
        Page<ShoppingItem> all = shoppingItemRepository.findAllByUser_Id(userId, pageable);
        return all.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
    }

    //endregion

    //region DELETE METHODS
    @DeleteMapping("/user/shopping-list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ShoppingItemDto delete(@RequestBody ShoppingItem shoppingItem, Authentication authentication) {
        User user = shoppingItem.getUser();
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can save new shopping item.");
        }
        shoppingItemRepository.delete(shoppingItem);
        return shoppingItemMapper.toDto(shoppingItem, user.getId());
    }

    @DeleteMapping("/user/shopping-list/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ShoppingItemDto deleteById(@PathVariable Long id, Authentication authentication) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingItemNotFoundException("Shopping item not found for id: " + id));
        return delete(shoppingItem, authentication);
    }

    @DeleteMapping("/user/shopping-list/nullify")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public Page<ShoppingItemDto> deleteAllOwning(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<ShoppingItem> allByUserUsername = shoppingItemRepository.findAllByUser_Username(username, pageable);
        shoppingItemRepository.deleteAll(allByUserUsername);
        Page<ShoppingItemDto> dto = allByUserUsername.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
        return dto;
    }
    //endregion
}

