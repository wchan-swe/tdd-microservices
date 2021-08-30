Feature: Rating Control

  Scenario: Rating Control level decision to read book

    Given I am a customer with a set rating control of 12
    When I request to read an equal level book B1234
    Then I get the decision to read the book