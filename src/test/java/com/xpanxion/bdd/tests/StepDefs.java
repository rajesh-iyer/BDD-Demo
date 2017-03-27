package com.xpanxion.bdd.tests;

import com.xpanxion.bdd.demo.Addition;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class StepDefs {

    Addition addition = null;
    int result = 0;

    @Given("^I am on the Addition page$")
    public void i_am_on_the_Addition_page() throws Throwable {
        addition = new Addition();
    }

    @When("^I add the numbers \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_add_the_numbers_and(String arg1, String arg2) throws Throwable {
        result = addition.getResult(arg1, arg2);
    }

    @Then("^I should get the result as \"([^\"]*)\"$")
    public void i_should_get_the_result_as(String arg1) throws Throwable {
        Assert.assertEquals(Integer.parseInt(arg1), result);
    }
}
