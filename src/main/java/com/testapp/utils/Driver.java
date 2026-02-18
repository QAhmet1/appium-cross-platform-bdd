package com.testapp.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public class Driver {
    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    private Driver() {}

    public static AppiumDriver getDriver() {
        if (Objects.isNull(driverPool.get())) {
            // STEP 2: Priority -> System Property (Parallel), then Config File (Single Run)
            String platform = System.getProperty("platform");
            if (platform == null) {
                platform = ConfigReader.getProperty("platform");
            }
            platform = platform.toLowerCase();

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
                    default:
                        throw new RuntimeException("Unsupported platform: " + platform);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Appium Server URL is invalid!", e);
            }
        }
        return driverPool.get();
    }

    public static void closeDriver() {
        if (Objects.nonNull(driverPool.get())) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}