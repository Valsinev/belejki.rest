package com.example.belejki.demo.repository;

import com.example.belejki.demo.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
}
