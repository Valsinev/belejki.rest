package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
