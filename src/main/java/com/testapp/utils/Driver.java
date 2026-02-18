package com.testapp.utils;

import io.appium.java_client.AppiumDriver;
import java.util.Objects;

public class Driver {
    /**
     * ThreadLocal creates a separate instance of the driver for each thread.
     * This is essential for parallel execution to prevent session conflicts.
     */
    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    private Driver() {
        // Private constructor to prevent instantiation
    }

    public static AppiumDriver getDriver() {
        return driverPool.get();
    }

    public static void setDriver(AppiumDriver driver) {
        driverPool.set(driver);
    }

    public static void closeDriver() {
        if (Objects.nonNull(driverPool.get())) {
            driverPool.get().quit();
            driverPool.remove(); // Removes the instance from the current thread's memory
        }
    }
}