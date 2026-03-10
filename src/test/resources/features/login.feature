Feature: User Login Functionality

  @smoke
  Scenario: Successful login on QA Test App
    Given the user launches the application
    When the user enters credentials on "login" screen
    Then the user should see the dashboard

  @negative
  Scenario Outline: Verify error messages for "<desc>"
    Given the user launches the application
    When the user enters credentials id: "<id>" and token: "<token>"
    Then the error message should be displayed

    Examples:
      | id    | token | desc                |
      | admin |       | empty token         |
      |       | 1234  | empty engineer id   |
      | wrong | 1234  | invalid id          |
      |       |       | empty credentials   |