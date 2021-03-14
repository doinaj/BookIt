Feature: User info


  @db
  Scenario: Verify username on the UI matches with DB username
    Given User should be on the login page
    When User inputs "bbursnoll8d@acquirethisname.com" , "johndillinger" and clicks on sign in
    And User goes to self page
    Then User info on UI should match with DB user info for user "bbursnoll8d@acquirethisname.com"
