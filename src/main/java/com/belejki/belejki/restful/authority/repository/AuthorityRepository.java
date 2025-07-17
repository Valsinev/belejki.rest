package com.belejki.belejki.restful.authority.repository;

import com.belejki.belejki.restful.authority.domain.Authority;
import com.belejki.belejki.restful.authority.domain.UserRoles;
import com.belejki.belejki.restful.user.domain.User;
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

    Page<Authority> findAllByAuthority(UserRoles role, Pageable pageable);

	void deleteAllByUser_Username(String username);
}
