Feature: Employee Leave Management
  As an employee
  I want to apply for leave, view my leave history and check my leave balance
  So that I can manage my time off effectively

  Background:
    Given the system is initialized with leave types and balances
    And I am authenticated as employee "employee1" with password "pass123"

  Scenario: Employee views dashboard with leave balances
    When the employee requests the dashboard
    Then the response should contain a list of leave balances
    And the leave balance list should not be empty

  Scenario: Employee successfully applies for leave with sufficient balance
    When the employee applies for leave type 1 from "2025-08-01" to "2025-08-03" with reason "Family vacation"
    Then the leave application response should be "Leave Applied Successfully."

  Scenario: Employee applies for leave with insufficient balance
    When the employee applies for leave type 1 from "2025-08-01" to "2025-08-30" with reason "Long vacation"
    Then the leave application response should be "Insufficient leave balance."

  Scenario: Employee views leave history
    Given the employee has previously applied for leave
    When the employee requests their leave history
    Then the response should contain a list of leave applications

  Scenario: Employee applies for Sick leave
    When the employee applies for leave type 2 from "2025-09-10" to "2025-09-11" with reason "Medical checkup"
    Then the leave application response should be "Leave Applied Successfully."

  Scenario: Employee applies for leave with single day
    When the employee applies for leave type 1 from "2025-10-01" to "2025-10-01" with reason "Personal errand"
    Then the leave application response should be "Leave Applied Successfully."
