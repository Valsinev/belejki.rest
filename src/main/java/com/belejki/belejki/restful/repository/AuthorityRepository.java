package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Authority;
import com.belejki.belejki.restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(path = "authorities")
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findAllByUser(User user);
    List<Authority> findAllByUser_Username(String username);

    Page<Authority> findAllByUser_Username(String username, Pageable pageable);

    Page<Authority> findAllByAuthority(String role, Pageable pageable);

    Page<Authority> findAllByAuthorityAndUserIsNotNull(String role, Pageable pageable);

    Page<Authority> findAllByUserIsNotNull(Pageable pageable);

    Page<Authority> findAllByUserIsNull(Pageable pageable);

    Authority findByIdAndUserIsNotNull(Long id);
}
