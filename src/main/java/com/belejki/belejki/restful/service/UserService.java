package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.UserDto;
import com.belejki.belejki.restful.dto.UserPatchDto;
import com.belejki.belejki.restful.entity.Authority;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.UserAlreadyExistsException;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.mapper.UserMapper;
import com.belejki.belejki.restful.repository.FriendshipRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, FriendshipRepository friendshipRepository, PasswordEncoder passwordEncoder, AuthorityService authorityService, UserMapper userMapper) {
        this.userRepository = repository;
        this.friendshipRepository = friendshipRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User createUser(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLastLogin(LocalDate.now());
        user.setAuthorities(List.of(new Authority(null, user, UserRoles.ROLE_USER.name())));
        user.setEnabled(true);
        return userRepository.save(user);
    }


    public User update(User user, UserDto updatedUser) {

        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(user);
    }

    public User patchUser(Long id, UserPatchDto dto) {
        User user = findById(id);

        // Update only fields that are provided
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findByEnabledFalse(Pageable pageable) {
        return userRepository.findByEnabledFalse(pageable);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id: " + id));
    }




    @Transactional
    public User delete(User user) {
        //setting the linked table constraints to null
        List<Friendship> byUser = friendshipRepository.findByUser(user);
        byUser.forEach(friendship -> friendship.setFriend(null));
        List<Friendship> allByFriendUsername = friendshipRepository.findAllByFriend_Username(user.getUsername());
        allByFriendUsername.forEach(friendship -> friendship.setUser(null));

        //deleting relations in linked table friendships
        friendshipRepository.deleteAll(byUser);
        friendshipRepository.deleteAll(allByFriendUsername);

        userRepository.delete(user);
        return user;
    }

    public User deleteByUsername(String username) {
        User user = findByUsername(username);
        return delete(user);
    }

    public User deleteById(Long id) {
        User user = findById(id);

        return delete(user);
    }

    public List<User> deleteAllByIsSetForDeletion() {
        List<User> allBySetForDeletionTrue = userRepository.findAllBySetForDeletionTrue();
        allBySetForDeletionTrue.forEach(this::delete);
        return allBySetForDeletionTrue;
    }


}
