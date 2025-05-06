package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.UserAdminDto;
import com.belejki.belejki.restful.dto.UserDto;
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

    @PutMapping("/user/users/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserAdminDto updateUserByUsername(@PathVariable String username,
                                     @Valid @RequestBody UserDto user,
                                     Authentication authentication) {

        String loggedInUsername = authentication.getName();
        // Allow user to update only their own data, or admin can update anyone
        if (!loggedInUsername.equals(username) && !authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You cannot update other users.");
        }
        return userMapper.toAdminDto(userService.updateByUser_Username(username, user));
    }
    //endregion

    //region GET METHODS

    @GetMapping("/admin/users")
    public Page<UserAdminDto> findAll(Pageable pageable) {
        return userService.findAll(pageable).map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/disabled")
    public Page<UserAdminDto> findAllDisabled(Pageable pageable) {
        return userService.findByEnabledFalse(pageable).map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/set-for-deletion")
    public List<UserAdminDto> findAllBySetForDeletionTrue(Pageable pageable) {
        return userRepository.findAllBySetForDeletionTrue().stream().map(userMapper::toAdminDto).toList();
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
    public ResponseEntity<String> delete(@RequestBody User user, Authentication authentication) {
        String username = user.getUsername();
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this user.");
        }

        userService.delete(user);
        return ResponseEntity.ok("User '" + username + "' deleted successfully.");
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
    public ResponseEntity<List<UserAdminDto>> deleteAllByIsSetForDeletion(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (!isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete users.");
        }
        List<UserAdminDto> deleted = userService.deleteAllByIsSetForDeletion().stream().map(userMapper::toAdminDto).toList();
        return ResponseEntity.ok(deleted);
    }

    //endregion

}
