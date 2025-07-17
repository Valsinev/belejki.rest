package com.belejki.belejki.restful.user.service;

import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.user.web.dto.UserDetailsResponseDto;
import com.belejki.belejki.restful.user.web.dto.UserDetailsShortDto;
import com.belejki.belejki.restful.user.web.dto.UserPatchRequestDto;
import com.belejki.belejki.restful.user.web.dto.UserRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Locale;

public interface UserService {
	UserDetailsResponseDto createUser(@Valid UserRequestDto user, Locale locale);

	UserRequestDto findByConfirmationToken(String token);

	UserDetailsResponseDto enable(UserRequestDto userDto);

	UserDetailsResponseDto update(Long id, @Valid UserRequestDto user);

	UserDetailsResponseDto patchUser(Long id, UserPatchRequestDto patchDto);

	UserDetailsResponseDto findByUsername(String name);

	Page<UserDetailsResponseDto> findAll(Pageable pageable);

	Page<UserDetailsResponseDto> findAllNotLoggedBefore(int months, Pageable pageable);

	Page<UserDetailsResponseDto> findAllByConfirmationTokenNotNull(Pageable pageable);

	Page<UserDetailsResponseDto> findByEnabledFalse(Pageable pageable);

	Page<UserDetailsResponseDto> findAllBySetForDeletionTrue(Pageable pageable);

	UserDetailsResponseDto findById(Long id);

	Page<UserDetailsResponseDto> findAllByFirstNameContaining(String firstName, Pageable pageable);

	Page<UserDetailsResponseDto> findAllByLastNameContaining(String lastName, Pageable pageable);

	Page<UserDetailsResponseDto> findAllByFirstNameContainingAndLastNameContaining(String firstName, String lastName, Pageable pageable);

	void delete(User user);

	void deleteById(Long id);

	void deleteByUsername(String username);

	void deleteAllByIsSetForDeletion(Pageable pageable);

	void deleteAllNotLoggedBefore(int months, Pageable pageable);

	void deleteAllByConfirmationTokenNotNull(Pageable pageable);

	UserDetailsShortDto findCurrentUserBy_Username(String username);
}
