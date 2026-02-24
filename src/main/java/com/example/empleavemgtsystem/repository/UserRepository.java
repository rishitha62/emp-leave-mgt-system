// src/main/java/com/example/empleavemgtsystem/repository/UserRepository.java
package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}