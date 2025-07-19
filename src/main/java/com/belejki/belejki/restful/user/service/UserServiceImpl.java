package com.belejki.belejki.restful.user.service;

import com.belejki.belejki.restful.authority.service.AuthorityServiceImpl;
import com.belejki.belejki.restful.user.web.dto.UserDetailsResponseDto;
import com.belejki.belejki.restful.user.web.dto.UserDetailsShortDto;
import com.belejki.belejki.restful.user.web.dto.UserPatchRequestDto;
import com.belejki.belejki.restful.authority.domain.Authority;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.scheduler.service.EmailService;
import com.belejki.belejki.restful.shared.exception.user.UserAlreadyExistsException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.friendship.repository.FriendshipRepository;
import com.belejki.belejki.restful.user.repository.UserRepository;
import com.belejki.belejki.restful.user.web.dto.UserRegisterDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final String emailRegex = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, FriendshipRepository friendshipRepository, PasswordEncoder passwordEncoder, AuthorityServiceImpl authorityService, EmailService emailService, ModelMapper modelMapper) {
        this.userRepository = repository;
        this.friendshipRepository = friendshipRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
	    this.modelMapper = modelMapper;
    }


    //POST
    public UserDetailsResponseDto createUser(UserRegisterDto dto, Locale locale) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        }
        User user = modelMapper.map(dto, User.class);
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLastLogin(LocalDate.now());

        //if this is first registered user set admin authorities
        if (userRepository.count() == 0) {
            Authority userRole = new Authority(null, user, UserRoles.ROLE_USER);
            Authority adminRole = new Authority(null, user, UserRoles.ROLE_ADMIN);
            Authority rootAdminRole = new Authority(null, user, UserRoles.ROLE_ROOT_ADMIN);
            user.addAuthority(userRole);
            user.addAuthority(adminRole);
            user.addAuthority(rootAdminRole);
//            user.setAuthorities(Set.of(userRole, adminRole, rootAdminRole));
        } else {
            user.addAuthority(new Authority(null, user, UserRoles.ROLE_USER));
        }

        //Creates jwtToken and sets it to the user
        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));
        user.setLocale(locale.toLanguageTag());

        User saveduser = userRepository.save(user);

        emailService.sendConfirmationEmail(user.getUsername(), token, locale);

        UserDetailsResponseDto mapped = new UserDetailsResponseDto();
        try {
            mapped = modelMapper.map(saveduser, UserDetailsResponseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapped;
    }
    //PUT
    public UserDetailsResponseDto update(String username, UserRegisterDto updatedUser) throws UserNotFoundException{

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[UserService.update] User not found for username: " + username));

        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        User saved = userRepository.save(user);

        return modelMapper.map(saved, UserDetailsResponseDto.class);
    }

    //PATCH
    public UserDetailsResponseDto patchUser(UserPatchRequestDto dto) {

        Long id = dto.getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + id));

        // Update only fields that are provided
        if (dto.getUsername() != null && dto.getUsername().matches(emailRegex)) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
            user.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
            user.setLastName(dto.getLastName());
        }

        if (dto.getPassword() != null && dto.getPassword().length() >= 8) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDetailsResponseDto.class);
    }

    //GET
    public UserDetailsResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found for username: " + username));
        return modelMapper.map(user, UserDetailsResponseDto.class);
    }

    public UserDetailsResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found for id: " + id));

        return modelMapper.map(user, UserDetailsResponseDto.class);
    }

    @Override
    public UserDetailsShortDto findCurrentUserBy_Username(String username) {
        User user = userRepository.findByUsername(username).get();
        UserDetailsShortDto userDetails = modelMapper.map(user, UserDetailsShortDto.class);
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        userDetails.setAuthorities(authorities);
        boolean isAdmin = authorities.contains(UserRoles.ROLE_ADMIN.name());
        userDetails.setAdmin(isAdmin);
        return userDetails;
    }

    public UserDetailsResponseDto enable(UserRegisterDto userDto) {

        String username = userDto.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[UserService.enable] User not found for username: " + username));

        user.setEnabled(true);
        user.setConfirmationToken(null);
        user.setTokenExpiry(null);

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDetailsResponseDto.class);
    }


    public Page<UserDetailsResponseDto> findAll(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return all.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }


    public Page<UserDetailsResponseDto> findAllNotLoggedBefore(int months, Pageable pageable) {
        LocalDate cutoffDate = LocalDate.now().minusMonths(months);
        Page<User> allLastLoggedBefore = userRepository.findAllLastLoggedBefore(cutoffDate, pageable);
        return allLastLoggedBefore.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }

    public Page<UserDetailsResponseDto> findByEnabledFalse(Pageable pageable) {
        Page<User> byEnabledFalse = userRepository.findByEnabledFalse(pageable);
        return byEnabledFalse.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }


    public UserRegisterDto findByConfirmationToken(String token) {
        User byConfirmationToken = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(byConfirmationToken, UserRegisterDto.class);
    }

    public Page<UserDetailsResponseDto> findAllBySetForDeletionTrue(Pageable pageable) {

        Page<User> allBySetForDeletionTrue = userRepository.findAllBySetForDeletionTrue(pageable);
        return allBySetForDeletionTrue.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }

    public Page<UserDetailsResponseDto> findAllByFirstNameContaining(String firstName, Pageable pageable) {

        Page<User> allByFirstNameContaining = userRepository.findAllByFirstNameContaining(firstName, pageable);
        return allByFirstNameContaining.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }

    public Page<UserDetailsResponseDto> findAllByConfirmationTokenNotNull(Pageable pageable) {

        Page<User> allByConfirmationTokenNotNull = userRepository.findAllByConfirmationTokenNotNull(pageable);
        return allByConfirmationTokenNotNull.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }

    public Page<UserDetailsResponseDto> findAllByLastNameContaining(String lastName, Pageable pageable) {

        Page<User> allByLastNameContaining = userRepository.findAllByLastNameContaining(lastName, pageable);
        return allByLastNameContaining.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }

    public Page<UserDetailsResponseDto> findAllByFirstNameContainingAndLastNameContaining(String firstName, String lastName, Pageable pageable) {

        Page<User> allByFirstNameContainingAndLastNameContaining = userRepository.findAllByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
        return allByFirstNameContainingAndLastNameContaining.map(user -> modelMapper.map(user, UserDetailsResponseDto.class));
    }



    @Transactional
    public void delete(User user) {
        this.nullifyUserRelations(user);

        userRepository.delete(user);
    }

    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found for username: " + username));
        this.nullifyUserRelations(user);
        this.delete(user);
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("[UserService.deleteById] User not found for id: " + id));
        this.nullifyUserRelations(user);
        userRepository.deleteById(id);
    }

    private void nullifyUserRelations(User user) {
        friendshipRepository.deleteAllByUser_Id(user.getId());
        friendshipRepository.deleteAllByFriend_Id(user.getId());
    }

    @Override
    public void deleteAllByConfirmationTokenNotNull(Pageable pageable) {

        Page<User> allByConfirmationTokenNotNull = userRepository.findAllByConfirmationTokenNotNull(pageable);

        userRepository.deleteAll(allByConfirmationTokenNotNull);
    }


    public void deleteAllByIsSetForDeletion(Pageable pageable) {
        Page<User> allBySetForDeletionTrue = userRepository.findAllBySetForDeletionTrue(pageable);
        userRepository.deleteAll(allBySetForDeletionTrue);
    }


    @Override
    public void deleteAllNotLoggedBefore(int months, Pageable pageable) {

        LocalDate cutoff = LocalDate.now().minusMonths(months);

        Page<User> allLastLoggedBefore = userRepository.findAllLastLoggedBefore(cutoff, pageable);

        userRepository.deleteAll(allLastLoggedBefore);

    }

}
