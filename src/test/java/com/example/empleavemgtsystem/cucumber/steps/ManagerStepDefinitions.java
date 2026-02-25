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

public class ManagerStepDefinitions {

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

    @Given("I am authenticated as manager {string} with password {string}")
    public void authenticateManager(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username); body.put("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Map> response = restTemplate.postForEntity(
            baseUrl() + "/auth/login", new HttpEntity<>(body, headers), Map.class);
        state.jwtToken = (String) response.getBody().get("token");
    }

    @Given("employee {string} has a pending leave application")
    public void createPendingLeave(String username) {
        User employee = userRepository.findByUsername(username).orElseThrow();
        LeaveType annual = leaveTypeRepository.findByName("Annual").orElseThrow();
        LeaveBalance balance = leaveBalanceRepository.findByUserAndLeaveType(employee, annual);
        LeaveApplication app = new LeaveApplication();
        app.setApplicant(employee); app.setLeaveType(annual);
        app.setStartDate(LocalDate.of(2025,8,5)); app.setEndDate(LocalDate.of(2025,8,7));
        app.setReason("Manager test leave"); app.setStatus(LeaveStatus.PENDING);
        app.setAppliedDate(LocalDate.now());
        state.pendingLeaveId = leaveApplicationRepository.save(app).getId();
        if (balance != null) {
            balance.setBalance(Math.max(0, balance.getBalance() - 3));
            leaveBalanceRepository.save(balance);
        }
    }

    @Given("there is a pending leave with id stored")
    public void pendingLeaveIdIsStored() {
        Assertions.assertNotNull(state.pendingLeaveId);
    }

    @Given("a leave application has already been approved")
    public void createAlreadyApprovedLeave() {
        User employee = userRepository.findByUsername("employee1").orElseThrow();
        User manager = userRepository.findByUsername("manager1").orElseThrow();
        LeaveType annual = leaveTypeRepository.findByName("Annual").orElseThrow();
        LeaveApplication app = new LeaveApplication();
        app.setApplicant(employee); app.setLeaveType(annual);
        app.setStartDate(LocalDate.of(2025,9,1)); app.setEndDate(LocalDate.of(2025,9,2));
        app.setReason("Pre-approved leave"); app.setStatus(LeaveStatus.APPROVED);
        app.setApprover(manager); app.setAppliedDate(LocalDate.now());
        app.setDecisionDate(LocalDate.now());
        state.pendingLeaveId = leaveApplicationRepository.save(app).getId();
    }

    @When("the manager requests pending leave applications")
    public void managerGetPendingLeaves() {
        HttpHeaders h = new HttpHeaders(); h.setBearerAuth(state.jwtToken);
        ResponseEntity<String> r = restTemplate.exchange(
            baseUrl() + "/manager/leaves/pending", HttpMethod.GET, new HttpEntity<>(h), String.class);
        state.lastResponse = r.getBody(); state.lastHttpStatus = r.getStatusCodeValue();
    }

    @Then("the response should contain a list of pending leaves")
    public void verifyPendingLeaveList() throws Exception {
        Assertions.assertNotNull(objectMapper.readValue(state.lastResponse, List.class));
    }

    @Then("the pending leaves list should not be empty")
    public void verifyPendingLeavesNotEmpty() throws Exception {
        Assertions.assertFalse(objectMapper.readValue(state.lastResponse, List.class).isEmpty());
    }

    @When("the manager approves the leave with comment {string}")
    public void managerApproveLeave(String comment) {
        Map<String, String> body = Map.of("comment", comment);
        HttpHeaders h = new HttpHeaders(); h.setBearerAuth(state.jwtToken);
        h.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> r = restTemplate.exchange(
            baseUrl() + "/manager/leaves/" + state.pendingLeaveId + "/approve",
            HttpMethod.PATCH, new HttpEntity<>(body, h), String.class);
        state.lastResponse = r.getBody(); state.lastHttpStatus = r.getStatusCodeValue();
    }

    @When("the manager rejects the leave with comment {string}")
    public void managerRejectLeave(String comment) {
        Map<String, String> body = Map.of("comment", comment);
        HttpHeaders h = new HttpHeaders(); h.setBearerAuth(state.jwtToken);
        h.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> r = restTemplate.exchange(
            baseUrl() + "/manager/leaves/" + state.pendingLeaveId + "/reject",
            HttpMethod.PATCH, new HttpEntity<>(body, h), String.class);
        state.lastResponse = r.getBody(); state.lastHttpStatus = r.getStatusCodeValue();
    }

    @Then("the approval response should be {string}")
    public void verifyApprovalResponse(String expected) {
        Assertions.assertEquals(expected, state.lastResponse.replace("\"", "").trim());
    }

    @Then("the rejection response should be {string}")
    public void verifyRejectionResponse(String expected) {
        Assertions.assertEquals(expected, state.lastResponse.replace("\"", "").trim());
    }

    @When("the manager requests the team leave history")
    public void managerGetTeamHistory() {
        HttpHeaders h = new HttpHeaders(); h.setBearerAuth(state.jwtToken);
        ResponseEntity<String> r = restTemplate.exchange(
            baseUrl() + "/manager/leaves/history", HttpMethod.GET, new HttpEntity<>(h), String.class);
        state.lastResponse = r.getBody(); state.lastHttpStatus = r.getStatusCodeValue();
    }

    @When("the manager requests a report from {string} to {string}")
    public void managerGetReport(String from, String to) {
        HttpHeaders h = new HttpHeaders(); h.setBearerAuth(state.jwtToken);
        ResponseEntity<String> r = restTemplate.exchange(
            baseUrl() + "/manager/reports?from=" + from + "&to=" + to,
            HttpMethod.GET, new HttpEntity<>(h), String.class);
        state.lastResponse = r.getBody(); state.lastHttpStatus = r.getStatusCodeValue();
    }

    @Then("the report response should be a list")
    public void verifyReportIsList() throws Exception {
        Assertions.assertNotNull(objectMapper.readValue(state.lastResponse, List.class));
    }
}
