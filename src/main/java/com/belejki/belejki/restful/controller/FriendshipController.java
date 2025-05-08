package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.FriendshipDto;
import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.exception.FriendshipNotFoundException;
import com.belejki.belejki.restful.mapper.FriendshipMapper;
import com.belejki.belejki.restful.repository.FriendshipRepository;
import com.belejki.belejki.restful.service.FriendshipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FriendshipController {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipService service;
    private final FriendshipMapper friendshipMapper;

    @Autowired
    public FriendshipController(FriendshipService service, FriendshipRepository friendshipRepository, FriendshipMapper friendshipMapper) {
        this.service = service;
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
    }

    //region POST METHODS

    @PostMapping("/user/friendships")
    public FriendshipDto save(@Valid @RequestBody FriendshipDto friendshipDto,
                              Authentication authentication) {
        String username = authentication.getName();
        boolean isOwner = username.equals(friendshipDto.getUsername());

        if (!isOwner) {
            throw new AccessDeniedException("You cannot create friendships for other users.");
        }

        return friendshipMapper.toDto(service.save(friendshipDto));
    }

    //endregion



    //region GET METHODS

    @GetMapping("/admin/friendships")
    public Page<FriendshipDto> findAll(Pageable pageable) {
        Page<Friendship> all = friendshipRepository.findAll(pageable);
        return all.map(friendshipMapper::toDto);
    }

    //returns friend name and List<Wish> wishlist of the friend(FriendshipDto)
    @GetMapping("/user/friendships/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public Page<FriendshipDto> findAllByUser_Username(@PathVariable String username,
                                                      Pageable pageable,
                                                      Authentication authentication) {
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can delete friendship with username: " + username);
        }
        return service.findAllByUser_Username(username, pageable).map(friendshipMapper::toDto);
    }




    //endregion


    //region DELETE METHODS

    @DeleteMapping("/user/friendships")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ResponseEntity<FriendshipDto> delete(@RequestBody Friendship friendship, Authentication authentication) {
        String username = friendship.getUser().getUsername();
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can delete friendship.");
        }
        FriendshipDto dto = friendshipMapper.toDto(service.delete(friendship));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/user/friendships/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ResponseEntity<FriendshipDto> deleteById(@PathVariable Long id, Authentication authentication) {
        Friendship friendship = friendshipRepository.findById(id).orElseThrow(() -> new FriendshipNotFoundException("Friendship not found with id: " + id));
        String username = friendship.getUser().getUsername();
        boolean access = checkIfOwnerOrAdmin(authentication, username);
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can delete friendship with id: " + id);
        }
        Friendship deleted = service.deleteById(id);
        FriendshipDto dto = friendshipMapper.toDto(deleted);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/admin/friendships/friend/{friendUsername}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ResponseEntity<List<FriendshipDto>> deleteAllByFriend_Username(@PathVariable String friendUsername,
                                                                          Authentication authentication,
                                                                          Pageable pageable) {
        Page<Friendship> allByFriendUsername = friendshipRepository.findAllByFriend_Username(friendUsername, pageable);
        if (allByFriendUsername == null) {
            throw new RuntimeException("No friendships found for friend name: " + friendUsername);
        }
        List<FriendshipDto> deletedFriendships = new ArrayList<>();
        allByFriendUsername.forEach(friendship ->
        {
            boolean access = checkIfOwnerOrAdmin(authentication, friendship.getUser().getUsername());
            if (access) {
                service.delete(friendship);
                deletedFriendships.add(friendshipMapper.toDto(friendship));
            }
        });
        return ResponseEntity.ok(deletedFriendships);
    }

    @DeleteMapping("/admin/friendships/user/{username}")
    public ResponseEntity<List<FriendshipDto>> deleteAllByUser(@PathVariable String username,
                                                               Authentication authentication,
                                                               Pageable pageable) {
        Page<Friendship> allByUsername = friendshipRepository.findAllByUser_Username(username, pageable);
        if (allByUsername == null) {
            throw new RuntimeException("No friendships found for friend name: " + username);
        }
        List<FriendshipDto> deletedFriendships = new ArrayList<>();
        allByUsername.forEach(friendship ->
        {
            boolean access = checkIfOwnerOrAdmin(authentication, friendship.getUser().getUsername());
            if (access) {
                service.delete(friendship);
                deletedFriendships.add(friendshipMapper.toDto(friendship));
            }
        });
        return ResponseEntity.ok(deletedFriendships);
    }


    //endregion


    private static boolean checkIfOwnerOrAdmin(Authentication authentication, String username) {
        String authenticated = authentication.getName();
        boolean isOwner = authenticated.equals(username);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        // Only allow deletion if the logged-in user is the same OR the user is an admin
        if (isOwner || isAdmin) {
            return true;
        } else return false;
    }

}
