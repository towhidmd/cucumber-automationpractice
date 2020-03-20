Feature: Sanity Test of Automation Practice Website
  #Given, When, Then, And, But
  Background: This is a set up before each scenario
    Given I Navigate to 'automation practice' website in the browser

  @sanity
  Scenario: Navigation to WOMEN DRESSES tab successful
    Given I hover-over on 'WOMEN' tab in home page
    And I click on 'Evening dresses' tab in navigation menu
    Then I see 'Evening Dresses'  title in evening dresses page
