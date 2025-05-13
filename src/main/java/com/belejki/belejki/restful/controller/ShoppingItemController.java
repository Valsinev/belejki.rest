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


import java.util.Optional;

import static com.belejki.belejki.restful.controller.Utility.checkIfOwnerOrAdmin;

@RestController
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
    ShoppingItemDto save(@Valid @RequestBody ShoppingItemDto dto, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        ShoppingItem entity = shoppingItemMapper.toEntity(dto, user);
        ShoppingItem saved = shoppingItemRepository.save(entity);
        dto.setUserId(saved.getUser().getId());
        dto.setId(saved.getId());
        return dto;
    }

    //endregion

    //region GET METHODS


    @GetMapping("/admin/shopping-list/user/id/{userId}")
    Page<ShoppingItemDto> findAllForUser_Id(@PathVariable Long userId, Pageable pageable) {
        Page<ShoppingItem> all = shoppingItemRepository.findAllByUser_Id(userId, pageable);
        return all.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
    }

    @GetMapping("/admin/shopping-list/user/{username}")
    Page<ShoppingItemDto> findAllForUser_Username(@PathVariable String username, Pageable pageable) {
        Page<ShoppingItem> all = shoppingItemRepository.findAllByUser_Username(username, pageable);
        return all.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
    }

    @GetMapping("/user/shopping-list")
    Page<ShoppingItemDto> findAll(Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<ShoppingItem> all = shoppingItemRepository.findAllByUser_Username(username, pageable);
        return all.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
    }

    @GetMapping("/user/shopping-list/{id}")
    ShoppingItemDto findById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        ShoppingItem founded = shoppingItemRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new ShoppingItemNotFoundException("No shopping item found for id: " + id));
        return shoppingItemMapper.toDto(founded, user.getId());
    }


    //endregion

    //region DELETE METHODS
    @DeleteMapping("/user/shopping-list")
    public ShoppingItemDto delete(@RequestBody ShoppingItem shoppingItem, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (!shoppingItem.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You dont have permission to delete this shopping item.");
        }
        shoppingItemRepository.delete(shoppingItem);
        return shoppingItemMapper.toDto(shoppingItem, user.getId());
    }

    @DeleteMapping("/user/shopping-list/{id}")
    public ShoppingItemDto deleteById(@PathVariable Long id, Authentication authentication) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingItemNotFoundException("Shopping item not found for id: " + id));
        return delete(shoppingItem, authentication);
    }

    @DeleteMapping("/user/shopping-list/empty")
    public Page<ShoppingItemDto> deleteAllOwning(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<ShoppingItem> allByUserUsername = shoppingItemRepository.findAllByUser_Username(username, pageable);
        shoppingItemRepository.deleteAll(allByUserUsername);
        Page<ShoppingItemDto> dto = allByUserUsername.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
        return dto;
    }

    @DeleteMapping("/admin/shopping-list/{id}")
    public ShoppingItemDto deleteByIdForAdmin(@PathVariable Long id) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingItemNotFoundException("Shopping item not found for id: " + id));
        shoppingItemRepository.delete(shoppingItem);
        return shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId());
    }

    @DeleteMapping("/admin/shopping-list/empty/user/{username}")
    public Page<ShoppingItemDto> deleteAllOwning(@PathVariable String username, Pageable pageable) {
        Page<ShoppingItem> allByUserUsername = shoppingItemRepository.findAllByUser_Username(username, pageable);
        shoppingItemRepository.deleteAll(allByUserUsername);
        Page<ShoppingItemDto> dto = allByUserUsername.map(shoppingItem -> shoppingItemMapper.toDto(shoppingItem, shoppingItem.getUser().getId()));
        return dto;
    }
    //endregion
}

