@US02
@DepositFeature
Feature: Initiate a deposit

  Background:
    Given I open the Digital Bank site
    And I accept cookies
    When I sign in using a "jjelli" and "1234Start"
    Then the Digital Bank home page opens
    And I go to the "Deposit" page

  Scenario: Successful deposit
    When the user selects the account "Main account (Standard Checking)"
    And enters the deposit amount "100.00"
    And clicks the "Deposit" button
    Then View Checking Accounts page is open
