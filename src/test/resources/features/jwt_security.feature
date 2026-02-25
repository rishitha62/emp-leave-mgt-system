Feature: JWT Security
  As a system
  I'want to validate JWT tokens
  So that only authenticated users can access protected resources

  Scenario: Valid JWT token is accepted
    Given a valid JWT token is generated for user "employee1" with role "EMPLOYEE"
    When the token is validated
    Then the token should be valid

  Scenario: Invalid JWT token is rejected
    Given an invalid JWT token string "invalid.token.string"
    When the token is validated
    Then the token should be invalid

  Scenario: JWT token contains correct username
    Given a valid JWT token is generated for user "manager1" with role "MANAGER"
    When the username is extracted from the token
    Then the extracted username should be "manager1"

  Scenario: JWT token contains correct roles
    Given a valid JWT token is generated for user "employee1" with role "EMPLOYEE"
    When the roles are extracted from the token
    Then the extracted roles should contain "EMPLOYEE"

  Scenario: Access protected endpoint without token returns 403
    When an unauthenticated request is made to "/employee/dashboard"
    Then the HTTP response status should be 403

  Scenario: Access employee endpoint with manager token returns 403
    Given a valid JWT token is generated for user "manager1" with role "MANAGER"
    When a request is made to "/employee/dashboard" with the manager token
    Then the HTTP response status should be 403
