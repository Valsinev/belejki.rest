package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "wishes")
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser_Username(String username);

    List<Wish> findByUser(User user);

    List<Wish> findByDescription(String description);
}
