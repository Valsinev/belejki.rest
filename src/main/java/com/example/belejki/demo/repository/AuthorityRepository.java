package com.example.belejki.demo.repository;

import com.example.belejki.demo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
