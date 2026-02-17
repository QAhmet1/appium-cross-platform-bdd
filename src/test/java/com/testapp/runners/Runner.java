package com.testapp.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure reporting plugin
                "json:target/cucumber.json"
        },
        features = "src/test/resources/features",
        glue = "com/testapp/stepdefinitions",
        tags = "@smoke"
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        /* * This enables TestNG to run Cucumber scenarios in parallel threads.
         */
        return super.scenarios();
    }
}