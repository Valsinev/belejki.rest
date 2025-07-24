package com.belejki.belejki.restful.friendship.service;

import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDetailResponseDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDto;
import com.belejki.belejki.restful.friendship.web.dto.FriendshipDetailResponseDto;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.shared.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.friendship.repository.FriendshipRepository;
import com.belejki.belejki.restful.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository repository, UserRepository userRepository,
                                 ModelMapper modelMapper) {
        this.friendshipRepository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FriendshipDetailResponseDto save(String username, String friendUsername) {

        //check if username adds himself as friend
        if (friendUsername.equals(username)) {
            throw new RuntimeException("[Friendship]: You cannot add yourself as friend.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("[Friendship]: USer not found for username: " + username));
        User friend = userRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new UserNotFoundException("[Friendship]: Friend not found for username: " + friendUsername));

        Friendship friendship = new Friendship(user, friend);

        this.checkIfFriendIsAlreadyAdded(friendship);

        user.addFriendship(friendship);
        Friendship saved = friendshipRepository.save(friendship);

        return modelMapper.map(saved, FriendshipDetailResponseDto.class);
    }

    @Override
    public Page<FriendshipDetailResponseDto> findAll(Pageable pageable) {
        Page<Friendship> all = friendshipRepository.findAll(pageable);
        return all.map((element) -> modelMapper.map(element, FriendshipDetailResponseDto.class));
    }

    public Page<FriendshipDetailResponseDto> findAllByUser_Username(String username, Pageable pageable) {
        Page<Friendship> allByUserUsername = friendshipRepository.findAllByUser_Username(username, pageable);
        return allByUserUsername.map((element) -> modelMapper.map(element, FriendshipDetailResponseDto.class));
    }

    public Page<FriendshipDetailResponseDto> findAllUserFriendshipsByFirstName(String username, String friendFirstName, Pageable pageable) {
        Page<Friendship> allByUserUsernameAndFriendFirstNameContaining = friendshipRepository.findAllByUser_UsernameAndFriend_firstNameContaining(username, friendFirstName, pageable);
        return allByUserUsernameAndFriendFirstNameContaining.map((element) -> modelMapper.map(element, FriendshipDetailResponseDto.class));
    }

    public FriendshipDetailResponseDto findById(Long id) {
        Friendship friendship = friendshipRepository.findById(id).orElseThrow(() -> new FriendshipNotFoundException("Friendship not found with id: " + id));
        return modelMapper.map(friendship, FriendshipDetailResponseDto.class);
    }


    private void checkIfFriendIsAlreadyAdded(Friendship friendship) {
        User user = friendship.getUser();
        List<String> friendsUsernames = user.getFriendships().stream().map(fr -> fr.getFriend().getUsername()).toList();
        if (friendsUsernames.contains(friendship.getFriend().getUsername())) {
            throw new RuntimeException("Friend already exist.");
        }
    }

    public void delete(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }

    public void deleteById(Long id) {
        friendshipRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByFriendshipAndUser_Username(FriendshipDto friendshipDto, String username) {
        friendshipRepository.deleteByIdAndUser_Username(friendshipDto.getId(), username);
    }

    @Transactional
    @Override
    public void deleteByIdAndUser_Username(Long id, String username) {
        friendshipRepository.deleteByIdAndUser_Username(id, username);
    }

    @Transactional
    @Override
    public void deleteAllByFriend_Username(String friendUsername) {
        friendshipRepository.deleteAllByFriend_Username(friendUsername);
    }

    @Transactional
    @Override
    public void deleteAllByUser_Username(String username) {
        friendshipRepository.deleteAllByUser_Username(username);
    }

    @Override
    public FriendshipDetailResponseDto findByFriend_Username(String username, String friendUsername) {
        Friendship friendship = friendshipRepository.findByUser_UsernameAndFriend_Username(username, friendUsername)
                .orElseThrow(() -> new FriendshipNotFoundException("[FriendshipService.findByFriend_Username] Friend not found."));
        return modelMapper.map(friendship, FriendshipDetailResponseDto.class);
    }

}
