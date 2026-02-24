package com.example.empleavemgtsystem.controller;

import com.example.empleavemgtsystem.dto.LeaveApplicationRequest;
import com.example.empleavemgtsystem.entity.LeaveBalance;
import com.example.empleavemgtsystem.entity.LeaveApplication;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.service.LeaveService;
import com.example.empleavemgtsystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public List<LeaveBalance> dashboard(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.getDashboard(user);
    }

    @PostMapping("/leaves")
    public String applyLeave(@RequestBody LeaveApplicationRequest request, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.applyLeave(user, request.getLeaveTypeId(),
                request.getStartDate(), request.getEndDate(), request.getReason());
    }

    @GetMapping("/leaves/history")
    public List<LeaveApplication> leaveHistory(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.getLeaveHistoryForEmployee(user);
    }
}
