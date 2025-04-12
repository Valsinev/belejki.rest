package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "authorities")
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
