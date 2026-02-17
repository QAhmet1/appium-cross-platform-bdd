package com.testapp.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {
    private static ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    private Driver() {} // Singleton

    public static AppiumDriver getDriver() {
        if (driverPool.get() == null) {
            String platform = ConfigReader.getProperty("platform").toLowerCase();
            String appPath = System.getProperty("user.dir") + "/" + ConfigReader.getProperty(platform + ".app.path");
            
            try {
                URL serverUrl = new URL("http://127.0.0.1:4723");
                
                switch (platform) {
                    case "android":
                        UiAutomator2Options androidOptions = new UiAutomator2Options()
                                .setDeviceName(ConfigReader.getProperty("android.device.name"))
                                .setApp(appPath)
                                .setAutomationName("UiAutomator2")
                                .setNewCommandTimeout(Duration.ofSeconds(60));
                        driverPool.set(new AndroidDriver(serverUrl, androidOptions));
                        break;
                        
                    case "ios":
                        XCUITestOptions iosOptions = new XCUITestOptions()
                                .setDeviceName(ConfigReader.getProperty("ios.device.name"))
                                .setApp(appPath)
                                .setAutomationName("XCUITest")
                                .setNewCommandTimeout(Duration.ofSeconds(60));
                        driverPool.set(new IOSDriver(serverUrl, iosOptions));
                        break;
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Appium Server URL hatalı!");
            }
        }
        return driverPool.get();
    }

    public static void quitDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}