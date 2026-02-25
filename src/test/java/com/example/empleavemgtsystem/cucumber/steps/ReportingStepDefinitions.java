package com.example.empleavemgtsystem.cucumber.steps;

import com.example.empleavemgtsystem.entity.*;
import com.example.empleavemgtsystem.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReportingStepDefinitions {

    @Autowired private UserRepository userRepository;
    @Autowired private LeaveTypeRepository leaveTypeRepository;
    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private LeaveApplicationRepository leaveApplicationRepository;
    @Autowired private SharedState state;
    @Autowired private ObjectMapper objectMapper;

    @Given("employee {string} has applied for leave in the current year")
    public void employeeAppliedLeaveCurrentYear(String username) {
        User employee = userRepository.findByUsername(username).orElseThrow();
        LeaveType annual = leaveTypeRepository.findByName("Annual").orElseThrow();
        LeaveApplication app = new LeaveApplication();
        app.setApplicant(employee); app.setLeaveType(annual);
        app.setStartDate(LocalDate.of(2025,6,1)); app.setEndDate(LocalDate.of(2025,6,3));
        app.setReason("Reporting test leave"); app.setStatus(LeaveStatus.APPROVED);
        app.setAppliedDate(LocalDate.of(2025,6,1));
        leaveApplicationRepository.save(app);
        LeaveBalance balance = leaveBalanceRepository.findByUserAndLeaveType(employee, annual);
        if (balance != null) {
            balance.setBalance(Math.max(0, balance.getBalance() - 3));
            leaveBalanceRepository.save(balance);
        }
    }

    @Then("the report should be empty")
    public void verifyReportEmpty() throws Exception {
        Assertions.assertTrue(objectMapper.readValue(state.lastResponse, List.class).isEmpty());
    }

    @Then("the report should not be empty")
    public void verifyReportNotEmpty() throws Exception {
        Assertions.assertFalse(objectMapper.readValue(state.lastResponse, List.class).isEmpty());
    }

    @Then("each report entry should contain fields {string}, {string}, {string}, {string}, {string}, {string}")
    public void verifyReportFields(String f1, String f2, String f3, String f4, String f5, String f6) throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(state.lastResponse,
            objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        if (!list.isEmpty()) {
            Map<String, Object> entry = list.get(0);
            for (String f : new String[]{f1, f2, f3, f4, f5, f6})
                Assertions.assertTrue(entry.containsKey(f), "Missing field: " + f);
        }
    }
}
