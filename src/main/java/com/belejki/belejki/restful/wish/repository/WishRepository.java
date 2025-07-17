package com.belejki.belejki.restful.wish.repository;

import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.wish.domain.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "wishes")
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser_Username(String username);

    List<Wish> findByUser(User user);

    List<Wish> findByDescription(String description);

    Page<Wish> findAllByUser_IdOrderByApproximatePriceDesc(Long id, Pageable pageable);

    Page<Wish> findAllByUser_UsernameOrderByApproximatePriceDesc(String username, Pageable pageable);


    Page<Wish> findAllByDescriptionContainingAndUser_UsernameOrderByApproximatePriceDesc(String description, String username, Pageable pageable);

    Page<Wish> findAllByApproximatePriceLessThanEqualAndUser_UsernameOrderByApproximatePriceDesc(Long price, String username, Pageable pageable);

    Page<Wish> findAllByUser_Id(Long id, Pageable pageable);

    Page<Wish> findAllByUser_Username(String username, Pageable pageable);

    Optional<Wish> findByIdAndUser_Username(Long id, String username);

    void deleteByIdAndUser_Username(Long id, String username);

    void deleteAllByUser_Id(Long id);

    void deleteAllByUser_Username(String username);
}
