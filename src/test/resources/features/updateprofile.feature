@US03
@UpdateProfileFeature
Feature: Update user profile
  As an authenticated user,
  I want to update my mobile number on the User Profile page
  so that my contact details stay current.

  Background:
    Given I open the Digital Bank site
    And I accept cookies
    When I sign in using a "jjelli" and "1234Start"
    Then the Digital Bank home page opens
    And I go to the "User Profile" page


  Scenario: Successful profile update with new mobile number
    When the user enters a new mobile number "(062) 012-3456"
    And clicks the "Submit" button
    Then the success message "Profile Updated Successfully." is displayed

