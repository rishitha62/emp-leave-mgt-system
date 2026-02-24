package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.LeaveApplication;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.entity.LeaveStatus;
import org.springframework.data.jpa.JpaRepository;
import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    List<LeaveApplication> findByApplicant(User user);
    List<LeaveApplication> findByApplicant_ManagerAndStatus(User manager, LeaveStatus status);
    List<LeaveApplication> findByApplicant_Manager(User manager);
}
