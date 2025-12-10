@US01
@LoginFeature
Feature: Login to the Digital Bank web application
  Background:
    Given I open the Digital Bank site
    And I accept cookies

  Scenario: Valid login
    When I sign in using a "jjelli" and "1234Start"
    Then the Digital Bank home page opens
    And a "Welcome János" message is displayed

  Scenario Outline: Invalid logins
    Given I open the Digital Bank site
    And I accept cookies
    When I sign in using a "<username>" and "<password>"
    Then an error message is displayed with "Invalid credentials or access not granted due to user account"
    And I stay on the login page

    Examples:
      | username | password  |
      | jjelli   | 1234Start2 |
      |          | 1234Start  |
      | jjelli   |           |
