package com.xpanxion.bdd.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static WebDriver driver;

    public static void createWebDriverInstance() {
        String pathToDriver = "/Users/Drivers/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToDriver);

        driver = new ChromeDriver();
    }

    public static WebDriver getWebDriver() {
        return driver;
    }

    public static void closeWebDriver() {
        driver.close();
        driver.quit();

    }
}
