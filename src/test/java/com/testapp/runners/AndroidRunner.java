package com.testapp.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com/testapp/stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-android.html",
                "json:target/cucumber-android.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.testapp.listeners.AllurePlatformCucumberPlugin"
        }
)
public class AndroidRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

