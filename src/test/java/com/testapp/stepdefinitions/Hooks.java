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
        // Initializes the driver for the current thread (Android or iOS)
        Driver.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        // Check if scenario failed to take a screenshot for debugging
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed_Scenario_Screenshot");
            } catch (Exception e) {
                System.err.println("Screenshot could not be taken: " + e.getMessage());
            }
        }

        // Proper cleanup for the specific thread's driver
        Driver.closeDriver();
    }
}