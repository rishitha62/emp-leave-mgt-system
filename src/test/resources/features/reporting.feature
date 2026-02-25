Feature: Leave Reporting
  As a manager
  I want to generate leave reports for a specific date range
  So that I can track team attendance and leave utilization

  Background:
    Given the system is initialized with leave types and balances
    And I am authenticated as manager "manager1" with password "mgrpass"

  Scenario: Manager generates report with no leaves in date range
    When the manager requests a report from "2020-01-01" to "2020-01-31"
    Then the report response should be a list
    And the report should be empty

  Scenario: Manager generates report with leaves in date range
    Given employee "employee1" has applied for leave in the current year
    When the manager requests a report from "2025-01-01" to "2025-12-31"
    Then the report response should be a list
    And the report should not be empty

  Scenario: Manager generates report with correct fields
    Given employee "employee1" has applied for leave in the current year
    When the manager requests a report from "2025-01-01" to "2025-12-31"
    Then each report entry should contain fields "employee", "type", "startDate", "endDate", "days", "status"
