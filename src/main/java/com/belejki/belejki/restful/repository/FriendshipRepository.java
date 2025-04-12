package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
