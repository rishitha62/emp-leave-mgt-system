// src/main/java/com/example/empleavemgtsystem/service/ReportingService.java
package com.example.empleavemgtsystem.service;

import com.example.empleavemgtsystem.entity.*;
import com.example.empleavemgtsystem.repository.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

@Service
public class ReportingService {
    @Autowired private LeaveApplicationRepository leaveApplicationRepo;

    public List<Map<String, Object>> generateReport(User manager, LocalDate from, LocalDate to) {
        List<LeaveApplication> applications = leaveApplicationRepo.findByApplicant_Manager(manager);

        // Filter by date
        applications = applications.stream()
                .filter(a -> !a.getAppliedDate().isBefore(from) && !a.getAppliedDate().isAfter(to))
                .collect(Collectors.toList());

        Map<String, Integer> typeTotals = new HashMap<>();
        Map<Long, Integer> employeeTotals = new HashMap<>();

        for (LeaveApplication la : applications) {
            String type = la.getLeaveType().getName();
            int days = (int) (la.getEndDate().toEpochDay() - la.getStartDate().toEpochDay() + 1);
            typeTotals.put(type, typeTotals.getOrDefault(type, 0) + days);
            employeeTotals.put(la.getApplicant().getId(), employeeTotals.getOrDefault(la.getApplicant().getId(), 0) + days);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (LeaveApplication la : applications) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("employee", la.getApplicant().getName());
            entry.put("type", la.getLeaveType().getName());
            entry.put("startDate", la.getStartDate());
            entry.put("endDate", la.getEndDate());
            entry.put("days", (int) (la.getEndDate().toEpochDay() - la.getStartDate().toEpochDay() + 1));
            entry.put("status", la.getStatus().name());
            result.add(entry);
        }
        return result;
    }
}