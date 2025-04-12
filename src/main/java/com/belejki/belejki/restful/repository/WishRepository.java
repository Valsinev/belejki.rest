package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "wishes")
public interface WishRepository extends JpaRepository<Wish, Long> {
}
