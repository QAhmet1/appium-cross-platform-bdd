package com.testapp.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com/testapp/stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        }
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    // We set this to false because our parallel logic is managed by testng.xml
    public Object[][] scenarios() {
        return super.scenarios();
    }
}