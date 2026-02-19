Feature: Profile Screen Functionality
  As a user, I want to manage my profile and logout securely

  Background: User is logged in and on Profile Screen
    Given the user enters credentials on "login" screen
    And the user navigates to the Profile tab

  @profile @smoke
  Scenario: Verify profile UI elements
    Then the profile avatar should be visible
    And the terminate session button should be visible

  @profile @logout
  Scenario: Verify successful logout
    When the user logs out from the profile screen
    Then the user should see the login screen