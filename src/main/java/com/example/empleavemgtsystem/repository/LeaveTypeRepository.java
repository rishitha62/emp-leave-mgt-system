// src/main/java/com/example/empleavemgtsystem/repository/LeaveTypeRepository.java
package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByName(String annual);
}