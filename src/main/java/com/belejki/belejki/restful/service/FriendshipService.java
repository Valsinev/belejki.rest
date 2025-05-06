package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.repository.FriendshipRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    FriendshipRepository friendshipRepository;
    UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository repository, UserRepository userRepository) {
        this.friendshipRepository = repository;
        this.userRepository = userRepository;
    }

    public Page<FriendshipDto> findAllByUser_Username(String username, Pageable pageable) {
        Page<Friendship> byUserUsername = friendshipRepository.findAllByUser_Username(username, pageable);

             return byUserUsername
                        .map(
                                friendship ->
                                        new FriendshipDto(username, friendship.getFriend().getUsername(), friendship.getFriend().getWishList()
                                        ));

    }

    public FriendshipDto save(Friendship friendship) {
        checkIfUserOrFriendIsNull(friendship);
        Optional<User> findUser = userRepository.findById(friendship.getUser().getId());
        Optional<User> findFriend = userRepository.findById(friendship.getFriend().getId());
        checkIfUserOrFriendUserExistInTheDatabase(findFriend, findUser);
        User user = findUser.get();
        User friend = findFriend.get();
        checkIfFriendIsAlreadyAdded(friend, user);
        user.getFriendships().add(friendship);
        userRepository.save(user);
        return new FriendshipDto(user.getUsername(), friend.getUsername(), friend.getWishList());
    }

    private void checkIfFriendIsAlreadyAdded(User friend, User user) {
        List<String> friendNames = user.getFriendships().stream()
                .map(friendship -> friendship.getFriend().getUsername())
                .toList();

        if (friendNames.contains(friend.getUsername())) {
            throw new RuntimeException("Friend already exist.");
        }
    }

    private static void checkIfUserOrFriendUserExistInTheDatabase(Optional<User> findFriend, Optional<User> findUser) {
        if (findFriend.isEmpty()) {
            throw new RuntimeException("Friend not found in database.");
        }
        if (findUser.isEmpty()) {
            throw new RuntimeException("User not found in database");
        }
    }

    private static void checkIfUserOrFriendIsNull(Friendship friendship) {
        if (friendship.getFriend() == null || friendship.getUser() == null) {
            throw new NullPointerException("Trying to pass friendship with null user.");
        }
    }

    public ResponseEntity delete(Friendship friendship) {
        friendshipRepository.delete(friendship);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity deleteById(Long id) {
        Optional<Friendship> byId = friendshipRepository.findById(id);
        if (byId.isEmpty()) {
            throw new FriendshipNotFoundException("Friendship not found for id: " + id);
        }
        friendshipRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity deleteAllByUser_Username(String username) {

        List<Friendship> founded = friendshipRepository.findByUser_Username(username);
        friendshipRepository.deleteAll(founded);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity deleteByFriend_Username(String username) {
        List<Friendship> byFriendUsername = friendshipRepository.findAllByFriend_Username(username);
        friendshipRepository.deleteAll(byFriendUsername);
        return ResponseEntity.noContent().build();
    }
}
