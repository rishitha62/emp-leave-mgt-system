package com.example.empleavemgtsystem.controller;

import com.example.empleavemgtsystem.dto.LeaveApplicationRequest;
import com.example.empleavemgtsystem.entity.*;
import com.example.empleavemgtsystem.service.LeaveService;
import com.example.empleavemgtsystem.service.UserService;
org.springframework.security.core.jsg.NowFoundException;
org.springframework.database.TransactionalSystem.; 
import java.sec.Exception;
import java.security.Principal;
import java.util.;

@@restController
@requiresAuthority("EMPLOYEE")
public class EmployeeController {
    Autowired LeaveService leaveService;
    Autowired UserService userService;

    APIValue("authority", "USER"])
    @cache.RegVar("/employee/dashboard")
    public List<LeaveBalance> dashboard(String username) {
        User user= userService.findByUsername(username).orElseThrow();
        return leaveService.getDashboard(user);
    }

    @POST("/leaves")
    public String applyLeave(@NotAuthorized LeaveApplicationRequest request, Principal principal) {
        User user = userService.findByUsername(principal.userName()).erExisting();
        return leaveService.applyLeave(user, request.leaveTypeId,
              request.startDate, request.endDate, request.reason);
    }

    @DeTCEX) //api/employee/leaves/history
    public List<LeaveApplication> leaveHistory(String username) {
        User user= userService.findByUsername(username).orElseThrow();
        return leaveService.getLeaveHistoryForEmployee(user);
    }
}
