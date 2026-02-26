package com.example.empleavemgtsystem.cucumber.steps;

import com.example.empleavemgtsystem.entity.*;
import com.example.empleavemgtsystem.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

public class EmployeeStepDefinitions {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private UserRepository userRepository;
    @Autowired private LeaveTypeRepository leaveTypeRepository;
    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private LeaveApplicationRepository leaveApplicationRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SharedState state;
    @Autowired private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String baseUrl() { return "http://localhost:" + port; }

    @Given("the system is initialized with leave types and balances")
    public void initSystem() {
        LeaveType annual = leaveTypeRepository.findByName("Annual").orElseGet(() -> {
            LeaveType lt = new LeaveType(); lt.setName("Annual"); lt.setDefaultDays(15);
            return leaveTypeRepository.save(lt);
        });
        LeaveType sick = leaveTypeRepository.findByName("Sick").orElseGet(() -> {
            LeaveType lt = new LeaveType(); lt.setName("Sick"); lt.setDefaultDays(10);
            return leaveTypeRepository.save(lt);
        });
        User manager = userRepository.findByUsername("manager1").orElseGet(() -> {
            User m = new User(); m.setUsername("manager1");
            m.setPassword(passwordEncoder.encode("mgrpass")); m.setName("Manager One");
            m.setEmail("manager1@test.com"); m.setRoles(Set.of(Role.MANAGER));
            return userRepository.save(m);
        });
        User employee = userRepository.findByUsername("employee1").orElseGet(() -> {
            User e = new User(); e.setUsername("employee1");
            e.setPassword(passwordEncoder.encode("pass123")); e.setName("Employee One");
            e.setEmail("employee1@test.com"); e.setRoles(Set.of(Role.EMPLOYEE));
            e.setManager(manager); return userRepository.save(e);
        });
        if (leaveBalanceRepository.findByUserAndLeaveType(employee, annual) == null)
            leaveBalanceRepository.save(new LeaveBalance(employee, annual, 15));
        if (leaveBalanceRepository.findByUserAndLeaveType(employee, sick) == null)
            leaveBalanceRepository.save(new LeaveBalance(employee, sick, 10));
    }

    @Given("I am authenticated as employee {string} with password {string}")
    public void authenticateEmployee(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username); body.put("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Map> response = restTemplate.postForEntity(
            baseUrl() + "/auth/login", new HttpEntity<>(body, headers), Map.class);
        state.jwtToken = (String) response.getBody().get("token");
    }

    @When("the employee requests the dashboard")
    public void employeeGetDashboard() {
        HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(state.jwtToken);
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl() + "/employee/dashboard", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        state.lastResponse = response.getBody(); state.lastHttpStatus = response.getStatusCodeValue();
    }

    @Then("the response should contain a list of leave balances")
    public void verifyLeaveBalanceList() throws Exception {
        Assertions.assertNotNull(objectMapper.readValue(state.lastResponse, List.class));
    }

    @Then("the leave balance list should not be empty")
    public void verifyLeaveBalanceNotEmpty() throws Exception {
        Assertions.assertFalse(objectMapper.readValue(state.lastResponse, List.class).isEmpty());
    }

    @When("the employee applies for leave type {int} from {string} to {string} with reason {string}")
    public void applyLeave(int typeId, String startDate, String endDate, String reason) {
        Map<String, Object> body = new HashMap<>();
        body.put("leaveTypeId", typeId); body.put("startDate", startDate);
        body.put("endDate", endDate); body.put("reason", reason);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(state.jwtToken); headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl() + "/employee/leaves", new HttpEntity<>(body, headers), String.class);
        state.lastResponse = response.getBody(); state.lastHttpStatus = response.getStatusCodeValue();
    }

    @Then("the leave application response should be {string}")
    public void verifyLeaveResponse(String expected) {
        String actual = state.lastResponse.replace("\"", "").replace("\"", "").trim();
        Assertions.assertEquals(expected, actual);
    }

    @Given("the employee has previously applied for leave")
    public void employeeHasAppliedLeave() {
        User employee = userRepository.findByUsername("employee1").orElseThrow();
        LeaveType annual = leaveTypeRepository.findByName("Annual").orElseThrow();
        LeaveBalance balance = leaveBalanceRepository.findByUserAndLeaveType(employee, annual);
        if (balance != null && balance.getBalance() >= 2) {
            LeaveApplication app = new LeaveApplication();
            app.setApplicant(employee); app.setLeaveType(annual);
            app.setStartDate(LocalDate.of(2025,7,1)); app.setEndDate(LocalDate.of(2025,7,2));
            app.setReason("Test leave"); app.setStatus(LeaveStatus.PENDING);
            app.setAppliedDate(LocalDate.now());
            leaveApplicationRepository.save(app);
            balance.setBalance(balance.getBalance() - 2);
            leaveBalanceRepository.save(balance);
        }
    }

    @When("the employee requests their leave history")
    public void employeeGetLeaveHistory() {
        HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(state.jwtToken);
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl() + "/employee/leaves/history", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        state.lastResponse = response.getBody(); state.lastHttpStatus = response.getStatusCodeValue();
    }

    @Then("the response should contain a list of leave applications")
    public void verifyLeaveApplicationList() throws Exception {
        Assertions.assertNotNull(objectMapper.readValue(state.lastResponse, List.class));
    }
}
