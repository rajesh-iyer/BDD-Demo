package com.xpanxion.bdd.demo.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    By heading = By.xpath("//h1[text()='We Pioneered Rural Sourcing']");
    By ourWorkLink = By.linkText("OUR WORK");

    public boolean isHomePageDisplayed() {
        return isElementPresent(heading);
    }

    public OurWorkPage clickOurWorkLink() {
        waitForElement(ourWorkLink).click();
        return new OurWorkPage();
    }
}
