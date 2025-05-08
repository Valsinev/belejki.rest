package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.repository.FriendshipRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository repository, UserRepository userRepository) {
        this.friendshipRepository = repository;
        this.userRepository = userRepository;
    }

    public Page<Friendship> findAllByUser_Username(String username, Pageable pageable) {
        return friendshipRepository.findAllByUser_Username(username, pageable);
    }

    public Friendship findById(Long id) {
        return friendshipRepository.findById(id).orElseThrow(() -> new FriendshipNotFoundException("Friendship not found with id: " + id));
    }

    public Friendship save(FriendshipDto friendship) {
        String username = friendship.getUsername();
        String friendName = friendship.getFriendName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found for username: " + username));
        User friend = userRepository.findByUsername(friendName).orElseThrow(() -> new UserNotFoundException("User not found for username: " + friendName));
        checkIfFriendIsAlreadyAdded(friend, user);
        Friendship newFriendship = new Friendship(user, friend);
        user.addFriendship(newFriendship);
        userRepository.save(user);
        return newFriendship;
    }

    private void checkIfFriendIsAlreadyAdded(User friend, User user) {
        List<String> friendNames = user.getFriendships().stream()
                .map(friendship -> friendship.getFriend().getUsername())
                .toList();

        if (friendNames.contains(friend.getUsername())) {
            throw new RuntimeException("Friend already exist.");
        }
    }

    public Friendship delete(Friendship friendship) {
        friendshipRepository.delete(friendship);
        return friendship;
    }

    public Friendship deleteById(Long id) {
        Friendship byId = findById(id);
        return delete(byId);
    }
}
