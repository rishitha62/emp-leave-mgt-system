// src/main/java/com/example/empleavemgtsystem/repository/LeaveApplicationRepository.java
package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByApplicant(User user);
    List<LeaveApplication> findByApplicant_ManagerAndStatus(User manager, LeaveStatus status);
    List<LeaveApplication> findByApplicant_Manager(User manager);
}