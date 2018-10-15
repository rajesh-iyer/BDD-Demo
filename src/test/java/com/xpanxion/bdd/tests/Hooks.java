package com.xpanxion.bdd.tests;

import com.xpanxion.bdd.demo.DriverFactory;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.util.concurrent.TimeUnit;

public class Hooks {

    @Before
    public void setup() {
        System.out.println("Initialization here...");
        DriverFactory.createWebDriverInstance();
        DriverFactory.getWebDriver().get("http://www.xpanxion.com");
        DriverFactory.getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        DriverFactory.getWebDriver().manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        System.out.println("...Tear Down here");
        DriverFactory.closeWebDriver();
    }
}
