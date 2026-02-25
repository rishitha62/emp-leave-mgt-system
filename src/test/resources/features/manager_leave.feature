Feature: Manager Leave Management
  As a manager
  I want to view, approve, and reject leave applications of my team
  So that I can manage team attendance effectively

  Background:
    Given the system is initialized with leave types and balances
    And I am authenticated as manager "manager1" with password "mgrpass"
    And employee "employee1" has a pending leave application

  Scenario: Manager views pending leave applications
    When the manager requests pending leave applications
    Then the response should contain a list of pending leaves
    And the pending leaves list should not be empty

  Scenario: Manager approves a pending leave application
    Given there is a pending leave with id stored
    When the manager approves the leave with comment "Approved for vacation"
    Then the approval response should be "Leave approved."

  Scenario: Manager rejects a pending leave application
    Given there is a pending leave with id stored
    When the manager rejects the leave with comment "Team deadline conflict"
    Then the rejection response should be "Leave rejected."

  Scenario: Manager tries to approve an already processed leave
    Given a leave application has already been approved
    When the manager approves the leave with comment "Duplicate approval"
    Then the approval response should be "Leave already processed."

  Scenario: Manager views team leave history
    When the manager requests the team leave history
    Then the response should contain a list of leave applications

  Scenario: Manager generates a report for a date range
    When the manager requests a report from "2025-01-01" to "2025-12-31"
    Then the report response should be a list
