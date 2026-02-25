package com.example.empleavemgtsystem.cucumber.steps;

import com.example.empleavemgtsystem.entity.Role;
import com.example.empleavemgtsystem.entity.User;
import com.example.empleavemgtsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class AuthStepDefinitions {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SharedState state;
    @Autowired private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Given("the system has a registered user with username {string} and password {string}")
    public void registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setName("Test Employee");
            user.setEmail(username + "@test.com");
            user.setRoles(Set.of(Role.EMPLOYEE));
            userRepository.save(user);
        }
    }

    @Given("the system has a registered manager with username {string} and password {string}")
    public void registerManager(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setName("Test Manager");
            user.setEmail(username + "@test.com");
            user.setRoles(Set.of(Role.MANAGER));
            userRepository.save(user);
        }
    }

    @When("the user submits a login request with username {string} and password {string}")
    public void submitLogin(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl() + "/auth/login", entity, String.class);
        state.lastResponse = response.getBody();
        state.lastHttpStatus = response.getStatusCodeValue();
    }

    @Then("the response should contain a JWT token")
    public void verifyTokenPresent() throws Exception {
        Map<?, ?> map = objectMapper.readValue(state.lastResponse, Map.class);
        Assertions.assertTrue(map.containsKey("token"),
                "Response should contain token key. Got: " + state.lastResponse);
        String token = (String) map.get("token");
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
        state.jwtToken = token;
    }

    @Then("the response should contain the user roles")
    public void verifyRolesPresent() throws Exception {
        Map<?, ?> map = objectMapper.readValue(state.lastResponse, Map.class);
        Assertions.assertTrue(map.containsKey("roles"), "Response should contain roles key");
    }

    @Then("the response should contain an error message {string}")
    public void verifyErrorMessage(String expectedError) throws Exception {
        Map<?, ?> map = objectMapper.readValue(state.lastResponse, Map.class);
        Assertions.assertTrue(map.containsKey("error"),
                "Response should contain error key. Got: " + state.lastResponse);
        Assertions.assertEquals(expectedError, map.get("error"));
    }

    @Then("the response roles should include {string}")
    public void verifyRoleIncluded(String role) throws Exception {
        Map<?, ?> map = objectMapper.readValue(state.lastResponse, Map.class);
        Object roles = map.get("roles");
        Assertions.assertNotNull(roles);
        Assertions.assertTrue(roles.toString().contains(role),
                "Roles should include " + role + ". Got: " + roles);
    }
}
