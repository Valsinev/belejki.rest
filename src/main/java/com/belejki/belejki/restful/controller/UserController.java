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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, UserMapper userMapper, MessageSource messageSource) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.messageSource = messageSource;
    }


    //region POST METHODS

    @PostMapping("/user/users")
    public UserAdminDto save(@Valid @RequestBody UserDto user, Locale locale) {
        return userMapper.toAdminDto(userService.createUser(user, locale));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token, Locale locale) {
        Optional<User> userOpt = userRepository.findByConfirmationToken(token);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        User user = userOpt.get();

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired.");
        }

        user.setEnabled(true);
        user.setConfirmationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);

        String message = messageSource.getMessage("confirm.success", null, locale);
        return ResponseEntity.ok(message);
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

    @GetMapping("/user")
    public UserDto userDashboardEndpoint(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return userMapper.toDto(user);
    }

    @GetMapping("/admin/users")
    public Page<UserAdminDto> findAll(Pageable pageable) {
        return userService.findAll(pageable)
                .map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/not-logged/{months}")
    public Page<UserAdminDto> findAllNotLoggedBefore(@PathVariable int months, Pageable pageable) {
        Page<User> allNotLoggedBefore = userService.findAllNotLoggedBefore(months, pageable);
        return allNotLoggedBefore.map(userMapper::toAdminDto);
    }

    @GetMapping("/admin/users/not-confirmed")
    public Page<UserAdminDto> findAllNotConfirmed(Pageable pageable) {
        Page<User> notConfirmed = userRepository.findAllByConfirmationTokenNotNull(pageable);
        return notConfirmed.map(userMapper::toAdminDto);
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

    @DeleteMapping("/admin/users/not-logged/{months}")
    public ResponseEntity<Page<UserAdminDto>> deleteAllNotLoggedInYears(@PathVariable int months, Pageable pageable) {
        Page<User> expiredBeforeMonths = userService.findAllNotLoggedBefore(months, pageable);
        Page<UserAdminDto> list = expiredBeforeMonths.map(userMapper::toAdminDto);
        userRepository.deleteAll(expiredBeforeMonths);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/admin/users/not-confirmed")
    public ResponseEntity<Page<UserAdminDto>> deleteAllNotConfirmed(Pageable pageable) {
        Page<User> allByConfirmationTokenNotNull = userRepository.findAllByConfirmationTokenNotNull(pageable);
        userRepository.deleteAll(allByConfirmationTokenNotNull);
        return ResponseEntity.ok(allByConfirmationTokenNotNull.map(userMapper::toAdminDto));
    }


    //endregion




}
