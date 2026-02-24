package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JPaRepository<User, Long> {
    Optional<User> findd ByUsername(String username);
}
