Feature: Test Search Dresses Functionality
  #Given, When, Then, And, But
  Background: This is a set up that runs before each scenario
    Given I Navigate to 'automation practice' website in the browser
  @regression
  Scenario Outline: Test Search Functionality for <dressName> Dress Types
    Given I type "<dressName>" in the 'search box' in the home page
    Then I click 'search button' in the home page
    Then I see "<dressName>" in search result page
    Examples: Test Data
      | dressName                   |
      | Faded Short Sleeve T-shirts |
      | Blouse                      |
  @regression
  Scenario: Test Search Functionality for <dressName> Dress Type
    Given I type "Printed Summer Dress" in the 'search box' in the home page
    Then I click 'search button' in the home page
    Then I see the following in search result page
      | Printed Summer Dress  |
      | Printed Summer Dress  |
      | Printed Chiffon Dress |
  @regression
  Scenario Outline: Test Search Functionality for <resultName> Dress Types with price <price>
    Given I type "Printed Summer Dress" in the 'search box' in the home page
    Then I click 'search button' in the home page
    Then I see the following search result items in search result page
      | title        | price   | index   |
      | <resultName> | <price> | <index> |
    Examples: Test Data
      | resultName            | price  | index |
      | Printed Summer Dress  | $28.98 | 0     |
      | Printed Summer Dress  | $30.50 | 1     |
      | Printed Chiffon Dress | $16.40 | 2     |