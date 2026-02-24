package com.example.empleavemgtsystem.repository;

import com.example.empleavemgtsystem.entity.LeaveBalance;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.entity.LeaveType;
import org.springframework.data.jpa.JpaRepository;
import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByUser(User user);
    LeaveBalance findByUserAndLeaveType(User user, LeaveType leaveType);
}
