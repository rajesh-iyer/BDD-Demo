Feature: Xpanxion Website Validation

  @Smoke @Regression @Xpanxion
  Scenario: Validate the Xpanxion Website
    Given the user on Xpanxion HomePage
    When the user navigates to Our Work Page
    Then the user should be able to view the heading "What Our Customers Are Not Saying"
