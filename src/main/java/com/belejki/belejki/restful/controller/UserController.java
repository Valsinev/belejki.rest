package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.UserAdminDto;
import com.belejki.belejki.restful.dto.UserDto;
import com.belejki.belejki.restful.dto.UserPatchDto;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.mapper.UserMapper;
import com.belejki.belejki.restful.repository.UserRepository;
import com.belejki.belejki.restful.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    //region POST METHODS

    @PostMapping("/user/users")
    public UserAdminDto save(@Valid @RequestBody UserDto user) {
        return userMapper.toAdminDto(userService.createUser(user));
    }

    //endregion

    //region PUT METHODS

    @PutMapping("/user/users/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public UserAdminDto updateUserByUserId(@PathVariable Long id,
                                           @Valid @RequestBody UserDto user) {
        User byId = userService.findById(id);
        User updated = userService.update(byId, user);
        return userMapper.toAdminDto(updated);
    }

    //endregion

    //region PATCH METHODS
    @PatchMapping("/user/users/patch/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id") // Example access control
    public ResponseEntity<UserDto> patchUser(
            @PathVariable Long id,
            @Valid @RequestBody UserPatchDto patchDto) {

        User updatedUser = userService.patchUser(id, patchDto);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    //endregion

    //region GET METHODS

    @GetMapping("/admin/users")
    public Page<UserAdminDto> findAll(Pageable pageable) {
        return userService.findAll(pageable)
                .map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/disabled")
    public Page<UserAdminDto> findAllDisabled(Pageable pageable) {
        return userService.findByEnabledFalse(pageable).map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/set-for-deletion")
    public List<UserAdminDto> findAllBySetForDeletionTrue(Pageable pageable) {
        return userRepository.findAllBySetForDeletionTrue(pageable).stream().map(userMapper::toAdminDto).toList();
    }

    @GetMapping("/admin/users/{username}")
    public UserAdminDto findByUsername(@PathVariable String username) {
         return userMapper.toAdminDto(userService.findByUsername(username));
    }

    @GetMapping("/admin/users/id/{id}")
    public UserAdminDto findById(@PathVariable Long id) {
        return userMapper.toAdminDto(userService.findById(id));
    }

    @GetMapping("/admin/users/first-name/{firstName}")
    public Page<UserAdminDto> findAllByFirstNameContaining(@PathVariable String firstName, Pageable pageable) {
        return userRepository.findAllByFirstNameContaining(firstName, pageable).map(userMapper::toAdminDto);
    }


    @GetMapping("/admin/users/last-name/{lastName}")
    public Page<UserAdminDto> findAllByLastNameContaining(@PathVariable String lastName, Pageable pageable) {
        return userRepository.findAllByLastNameContaining(lastName, pageable).map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/first-and-last-name")
    public Page<UserAdminDto> findAllByFirstNameContainingAndLastNameContaining(@RequestParam String firstName,
                                                                        @RequestParam String lastName,
                                                                        Pageable pageable) {
        return userRepository.findAllByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable).map(userMapper::toAdminDto);
    }

    //endregion

    //region DELETE METHODS

    @DeleteMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> delete(@RequestBody User user, Authentication authentication) {
        String username = user.getUsername();
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this user.");
        }

        UserDto dto = userMapper.toDto(userService.delete(user));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/admin/users/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAdminDto> deleteById(@PathVariable Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this user.");
        }

        UserAdminDto adminDto = userMapper.toAdminDto(userService.deleteById(id));
        return ResponseEntity.ok(adminDto);
    }

    @DeleteMapping("/admin/users/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAdminDto> deleteByUsername(@PathVariable String username, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (!loggedInUsername.equals(username) && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this user.");
        }
        UserAdminDto adminDto = userMapper.toAdminDto(userService.deleteByUsername(username));
        return ResponseEntity.ok(adminDto);
    }

    @DeleteMapping("/admin/users/set-for-deletion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserAdminDto>> deleteAllByIsSetForDeletion(Authentication authentication, Pageable pageable) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete users.");
        }
        Page<User> deleted = userService.deleteAllByIsSetForDeletion(pageable);
        return ResponseEntity.ok(deleted.map(userMapper::toAdminDto));
    }

    //endregion

    private boolean hasAccess(Authentication authentication, Long targetId) {
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getId().equals(targetId);
    }

    @GetMapping("/test-auth")
    public String test(Authentication authentication) {
        return "Hello " + authentication.getName() + ", roles: " +
                authentication.getAuthorities().toString();
    }




}
