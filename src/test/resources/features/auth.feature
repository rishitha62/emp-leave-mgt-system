Feature: Authentication
  As a user of the Employee Leave Management System
  I want to be able to login with valid credentials
  So that I receive a JWT token for subsequent requests

  Scenario: Successful login with valid credentials
    Given the system has a registered user with username "employee1" and password "pass123"
    When the user submits a login request with username "employee1" and password "pass123"
    Then the response should contain a JWT token
    And the response should contain the user roles

  Scenario: Failed login with invalid password
    Given the system has a registered user with username "employee1" and password "pass123"
    When the user submits a login request with username "employee1" and password "wrongpass"
    Then the response should contain an error message "Invalid credentials"

  Scenario: Failed login with non-existent user
    When the user submits a login request with username "ghost" and password "nopass"
    Then the response should contain an error message "Invalid credentials"

  Scenario: Successful login as Manager
    Given the system has a registered manager with username "manager1" and password "mgrpass"
    When the user submits a login request with username "manager1" and password "mgrpass"
    Then the response should contain a JWT token
    And the response roles should include "MANAGER"
