package com.xpanxion.bdd.demo.pages;

import org.openqa.selenium.By;

public class OurWorkPage extends BasePage {

    By subHeading = By.xpath("//h2[normalize-space(text())='What Our Customers Are Saying']");

    public boolean isSubHeadingPresent() {
        return isElementPresent(subHeading);
    }

    public String getSubHeading() {
        return waitForElement(subHeading).getText();
    }
}
