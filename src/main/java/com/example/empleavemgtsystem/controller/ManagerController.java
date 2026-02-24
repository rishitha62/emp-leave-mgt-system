// src/main/java/com/example/empleavemgtsystem/controller/ManagerController.java
package com.example.empleavemgtsystem.controller;

import com.example.empleavemgtsystem.dto.LeaveDecisionRequest;
import com.example.empleavemgtsystem.entity.*;
import com.example.empleavemgtsystem.service.LeaveService;
import com.example.empleavemgtsystem.service.ReportingService;
import com.example.empleavemgtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired private LeaveService leaveService;
    @Autowired private UserService userService;
    @Autowired private ReportingService reportingService;

    @GetMapping("/leaves/pending")
    public List<LeaveApplication> pendingLeaves(Principal principal) {
        User manager = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.getPendingLeavesForManager(manager);
    }

    @PatchMapping("/leaves/{id}/approve")
    public String approveLeave(@PathVariable Long id,
                               @RequestBody LeaveDecisionRequest request,
                               Principal principal) {
        User manager = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.approveLeave(id, manager, request.comment);
    }

    @PatchMapping("/leaves/{id}/reject")
    public String rejectLeave(@PathVariable Long id,
                              @RequestBody LeaveDecisionRequest request,
                              Principal principal) {
        User manager = userService.findByUsername(principal.getName()).orElseThrow();
        return leaveService.rejectLeave(id, manager, request.comment);
    }

    @GetMapping("/leaves/history")
    public List<LeaveApplication> leaveHistory(@RequestParam(required = false) Long employeeId, Principal principal) {
        User manager = userService.findByUsername(principal.getName()).orElseThrow();
        if (employeeId != null) {
            User employee = userService.findByUsername(manager.getUsername()).orElseThrow();
            return leaveService.getLeaveHistoryForEmployee(employee);
        }
        return leaveService.getLeaveHistoryForManager(manager);
    }

    @GetMapping("/reports")
    public List<Map<String, Object>> report(@RequestParam String from,
                                            @RequestParam String to,
                                            Principal principal) {
        User manager = userService.findByUsername(principal.getName()).orElseThrow();
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return reportingService.generateReport(manager, fromDate, toDate);
    }
}