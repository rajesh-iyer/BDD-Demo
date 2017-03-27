package com.xpanxion.bdd.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, features = "src/test/resources/features", plugin = {"pretty",
    "json:target/cucumber.json"}, tags = {"~@ignore"}, glue = "com.xpanxion.bdd.tests")
public class RunBDDTest {

}
