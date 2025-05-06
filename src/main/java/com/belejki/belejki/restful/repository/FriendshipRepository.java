package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Friendship;
import com.belejki.belejki.restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser_Username(String username);

    List<Friendship> findByUser(User user);

    Friendship findByFriend_Username(String username);

    Page<Friendship> findAllByUser_Username(String username, Pageable pageable);

    List<Friendship> findAllByFriend_Username(String username);
}
