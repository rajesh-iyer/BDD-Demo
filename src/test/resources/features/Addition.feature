Feature: Addition of numbers

    @Smoke
    @Regression
    @Addition
    Scenario Outline: Positive Scenarios for adding two numbers
        Given I am on the Addition page
        When I add the numbers "<number1>" and "<number2>"
        Then I should get the result as "<result>"
        Examples:
        |number1|number2|result|
        |1|2|3|
        |-1|2|1|
        |-1|-2|-3|
        |10|20|30|