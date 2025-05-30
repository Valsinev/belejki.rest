package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.dto.WishDto;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "wishes")
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser_Username(String username);

    List<Wish> findByUser(User user);

    List<Wish> findByDescription(String description);

    Page<Wish> findAllByUser_Id(Long id, Pageable pageable);

    Page<Wish> findAllByUser_Username(String username, Pageable pageable);


    Page<Wish> findAllByDescriptionContainingAndUser_Username(String description, String username, Pageable pageable);

    Page<Wish> findAllByApproximatePriceLessThanAndUser_Username(Long price, String username, Pageable pageable);
}
