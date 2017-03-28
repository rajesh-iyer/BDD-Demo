Feature: Subtraction Functionality

  @Smoke
  @Regression
  @Subtraction
  Scenario Outline: Subtract two numbers
    Given I am on the Subtraction page
    When I subtract "<number2>" from "<number1>"
    Then I should get the result as "<result>"

    Examples: 
      | number1 | number2 | result |
      | 30      | 20      | 10     |
      | 2       | 5       | -3     |
      | 0       | 40      | -40    |
