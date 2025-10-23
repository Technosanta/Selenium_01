Feature: Registration
  Verify user can fill registration form on demo site

  Scenario Outline: Register new user
    Given I am on the demo automation home page
    When I go to registration page
    And I fill registration with "<firstName>" "<lastName>" "<address>" "<email>" "<phone>" "<password>"
    Then the first name field should contain "<firstName>"

    Examples:
      | firstName | lastName | address        | email                  | phone       | password     |
      | John      | Doe      | 123 Main St    | john.doe@example.com   | 1234567890  | Password123  |
      | Jane      | Smith    | 456 Elm St     | jane.smith@example.com | 0987654321  | Secret456    |

