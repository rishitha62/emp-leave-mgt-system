package com.example.empleavemgtsystem.cucumber.steps;

import com.example.empleavemgtsystem.security.JwtUtil;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

public class JwtSecurityStepDefinitions {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private SharedState state;
    @Autowired private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl() { return "http://localhost:" + port; }

    @Given("a valid JWT token is generated for user {string} with role {string}")
    public void generateValidToken(String username, String role) {
        state.jwtToken = jwtUtil.generateToken(username, List.of(role));
    }

    @Given("an invalid JWT token string {string}")
    public void setInvalidToken(String token) {
        state.jwtToken = token;
    }

    @When("the token is validated")
    public void validateToken() {
        state.lastResponse = String.valueOf(jwtUtil.validateToken(state.jwtToken));
    }

    @Then("the token should be valid")
    public void verifyTokenValid() {
        Assertions.assertEquals("true", state.lastResponse);
    }

    @Then("the token should be invalid")
    public void verifyTokenInvalid() {
        Assertions.assertEquals("false", state.lastResponse);
    }

    @When("the username is extracted from the token")
    public void extractUsername() {
        state.lastResponse = jwtUtil.extractUsername(state.jwtToken);
    }

    @Then("the extracted username should be {string}")
    public void verifyExtractedUsername(String expected) {
        Assertions.assertEquals(expected, state.lastResponse);
    }

    @When("the roles are extracted from the token")
    public void extractRoles() {
        state.lastResponse = jwtUtil.extractRoles(state.jwtToken).toString();
    }

    @Then("the extracted roles should contain {string}")
    public void verifyRolesContain(String role) {
        Assertions.assertTrue(state.lastResponse.contains(role));
    }

    @When("an unauthenticated request is made to {string}")
    public void unauthenticatedRequest(String path) {
        state.lastHttpStatus = restTemplate.getForEntity(baseUrl() + path, String.class).getStatusCodeValue();
    }

    @Then("the HTTP response status should be {int}")
    public void verifyHttpStatus(int expected) {
        Assertions.assertEquals(expected, state.lastHttpStatus);
    }

    @When("a request is made to {string} with the manager token")
    public void requestWithManagerToken(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(state.jwtToken);
        state.lastHttpStatus = restTemplate.exchange(
            baseUrl() + path, HttpMethod.GET, new HttpEntity<>(headers), String.class).getStatusCodeValue();
    }
}
