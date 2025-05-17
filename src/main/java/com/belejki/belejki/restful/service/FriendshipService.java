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

    public Friendship save(Friendship friendship) {
        checkIfFriendIsAlreadyAdded(friendship);
        return friendshipRepository.save(friendship);
    }

    private void checkIfFriendIsAlreadyAdded(Friendship friendship) {
        User user = friendship.getUser();
        List<String> friendsUsernames = user.getFriendships().stream().map(fr -> fr.getFriend().getUsername()).toList();
        if (friendsUsernames.contains(friendship.getFriend().getUsername())) {
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
