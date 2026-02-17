package com.testapp.stepdefinitions;

import com.testapp.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp() {
        /* * Logic to be executed before every scenario. 
         * Driver initialization is handled by ThreadLocal Driver class.
         */
        Driver.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        /* * Checks if the scenario failed. If true, takes a screenshot 
         * and attaches it to the Allure Report.
         */
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed_Step_Screenshot");
        }
        
        /* * Quits the driver and removes it from ThreadLocal pool 
         * to prevent memory leaks and ensure parallel stability.
         */
        Driver.quitDriver();
    }
}