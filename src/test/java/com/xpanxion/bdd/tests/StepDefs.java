package com.xpanxion.bdd.tests;

import com.xpanxion.bdd.demo.Addition;
import com.xpanxion.bdd.demo.Subtract;
import com.xpanxion.bdd.demo.pages.HomePage;
import com.xpanxion.bdd.demo.pages.OurWorkPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class StepDefs {

    Addition addition = null;
    Subtract subtract = null;
    int result = 0;
    HomePage homePage;
    OurWorkPage ourWorkPage;

    @Given("^I am on the Addition page$")
    public void i_am_on_the_Addition_page() throws Throwable {
        addition = new Addition();
        //driver.get("http://www.addition.com");
    }

    @When("^I add the numbers \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_add_the_numbers_and(String arg1, String arg2) throws Throwable {
        result = addition.getResult(arg1, arg2);
        //driver.findElements(By.id("number1")).sendKeys(arg1);
        //driver.findElements(By.id("number2")).sendKeys(arg1);
        //driver.findElements(By.id("result")).click();
        //result = driver.findElements(By.id("result_txt")).getText();
    }

    @Then("^I should get the result as \"([^\"]*)\"$")
    public void i_should_get_the_result_as(String arg1) throws Throwable {
        Assert.assertEquals(Integer.parseInt(arg1), result);
    }

    @Given("^I am on the Subtraction page$")
    public void i_am_on_the_Subtraction_page() throws Throwable {
        subtract = new Subtract();
    }

    @When("^I subtract \"([^\"]*)\" from \"([^\"]*)\"$")
    public void i_subtract_from(String arg1, String arg2) throws Throwable {
        result = subtract.getResult(arg2, arg1);
    }

    @Given("^the user on Xpanxion HomePage$")
    public void the_user_on_Xpanxion_HomePage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        homePage = new HomePage();
        Assert.assertTrue("Xpanxion Home Page is not loaded", homePage.isHomePageDisplayed());
    }

    @When("^the user navigates to Our Work Page$")
    public void the_user_navigates_to_Our_Work_Page() throws Throwable {
        ourWorkPage = homePage.clickOurWorkLink();
    }

    @Then("^the user should be able to view the heading \"([^\"]*)\"$")
    public void the_user_should_be_able_to_view_the_heading(String headerText) throws Throwable {
        Assert.assertTrue("Our Work Page is not loaded", ourWorkPage.isSubHeadingPresent());
        Assert.assertEquals("The heading value is incorrect", headerText, ourWorkPage.getSubHeading());
    }
}
