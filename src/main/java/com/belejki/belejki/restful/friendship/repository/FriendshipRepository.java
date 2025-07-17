package com.belejki.belejki.restful.friendship.repository;

import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser_Username(String username);

    List<Friendship> findByUser(User user);

    Friendship findByFriend_Username(String username);


    Page<Friendship> findAllByFriend_Username(String username, Pageable pageable);
    List<Friendship> findAllByFriend_Username(String username);

    Page<Friendship> findAllByUser_Username(String username, Pageable pageable);
    Page<Friendship> findAllByUser_FirstName(String username, Pageable pageable);

    Page<Friendship> findAllByUser_UsernameAndFriend_firstNameContaining(String username, String friendFirstName, Pageable pageable);

    void deleteByIdAndUser_Username(Long id, String username);

    void deleteAllByFriend_Username(String friendUsername);

    void deleteAllByUser_Username(String username);
}
