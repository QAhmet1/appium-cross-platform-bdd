Feature: User Login Functionality

  @smoke
  Scenario: Successful login on QA Test App
    Given the user launches the application
    When the user enters credentials on "login" screen
    Then the user should see the dashboard