Feature: Log in
  As a user I should be able to login using the provided credentials

  @login
  Scenario Outline: All users should be able to log in
    Given User should be on the login page
    When User inputs "<username>" , "<password>" and clicks on sign in
    Then User should be on the map page

    Examples:
      | username                        | password      |
      | bbursnoll8d@acquirethisname.com | johndillinger |
      | teachervasctoforstall@gmail.com | scottforstall |
      | emaynell8f@google.es             | besslebond    |
