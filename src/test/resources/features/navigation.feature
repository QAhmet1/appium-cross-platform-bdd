Feature: Bottom Tab Bar Navigation
  As a mobile app user
  I want to navigate through the bottom tab bar
  So that I can access different sections of the application

  Background: User is logged in
    Given the user enters credentials on "login" screen

  @navigation @smoke
  Scenario: Verify bottom tab bar items are functional
    Then the user clicks on "Dash" tab
    And the user clicks on "Assets" tab
    And the user clicks on "Types" tab
    And the user clicks on "Lab" tab
    And the user clicks on "Profile" tab