package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.repository.FriendshipRepository;
import com.belejki.belejki.restful.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendshipController {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipService service;

    @Autowired
    public FriendshipController(FriendshipService service, FriendshipRepository friendshipRepository) {
        this.service = service;
        this.friendshipRepository = friendshipRepository;
    }

    //region POST METHODS

    @PostMapping("/user/friendships")
    public FriendshipDto save(@RequestBody Friendship friendship) {
        return service.save(friendship);
    }

    //endregion



    //region GET METHODS

    @GetMapping("/admin/friendships")
    public Page<FriendshipDto> findAll(Pageable pageable) {
        Page<Friendship> all = friendshipRepository.findAll(pageable);
        return all
                .map(friendship -> new FriendshipDto(friendship.getUser().getUsername(), friendship.getFriend().getUsername()));
    }

    //returns friend name and List<Wish> wishlist of the friend(FriendshipDto)
    @GetMapping("/user/friendships/{username}")
    public Page<FriendshipDto> findAllByUser_Username(@PathVariable String username, Pageable pageable) {
        return service.findAllByUser_Username(username, pageable);
    }

    //endregion


    //region DELETE METHODS

    @DeleteMapping("/user/friendships")
    public ResponseEntity delete(@RequestBody Friendship friendship) {
        return service.delete(friendship);
    }

    @DeleteMapping("/user/friendships/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/user/friendships/friend/{username}")
    public ResponseEntity deleteAllByFriend_Username(@PathVariable String username) {
        return service.deleteByFriend_Username(username);
    }

    @DeleteMapping("/admin/friendships/user/{username}")
    public ResponseEntity deleteAllByUser(@PathVariable String username) {
        return service.deleteAllByUser_Username(username);
    }


    //endregion

}
