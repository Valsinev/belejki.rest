package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEnabledFalse();

    Page<User> findByEnabledFalse(Pageable pageable);

    Page<User> findAllByFirstNameContaining(String firstName, Pageable pageable);

    Page<User> findAllByLastNameContaining(String lastName, Pageable pageable);

    Page<User> findAllByFirstNameContainingAndLastNameContaining(String firstname, String lastName, Pageable pageable);

    boolean existsByUsername(@Email(message = "Invalid format for email address.") @NotBlank(message = "Username is required.") String username);

    Optional<User> findByUsername(String username);

    Page<User> findAllBySetForDeletionTrue(Pageable pageable);
}
