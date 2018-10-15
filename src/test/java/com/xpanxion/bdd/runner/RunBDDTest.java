package com.xpanxion.bdd.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false,
        features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports"},
        tags = {"@Xpanxion"},
        glue = "com.xpanxion.bdd.tests")
public class RunBDDTest {

}
