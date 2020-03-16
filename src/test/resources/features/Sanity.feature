Feature: Sanity Test of Automation Practice Website

  # Given, Then, And

  Background: This is a set up that runs before each scenario
    Given I navigate to 'automation practice 'website in the browser

  @dev
  Scenario: Navigation to WOMEN DRESSES tab successful
  Given I hoverover on 'WOMEN' tab in home page
    And I click on 'Evening Dresses' tab in navigation menu
    Then I see 'Evening Dress' title in evening dresses page