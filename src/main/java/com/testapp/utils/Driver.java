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
    private static final ThreadLocal<String> platformPool = new ThreadLocal<>();

    private Driver() {}

    public static void setPlatform(String platform) {
        if (platform == null || platform.isBlank()) {
            return;
        }
        platformPool.set(platform.trim().toLowerCase());
    }

    public static String getPlatform() {
        return platformPool.get();
    }

    public static void clearPlatform() {
        platformPool.remove();
    }

    public synchronized static AppiumDriver getDriver() {
        if (Objects.isNull(driverPool.get())) {
            // Priority: thread-local platform (TestNG parallel) -> system property -> config file (single run)
            String platform = platformPool.get();
            if (platform == null || platform.isBlank()) {
                platform = System.getProperty("platform");
            }
            if (platform == null || platform.isBlank()) {
                platform = ConfigReader.getProperty("platform");
            }
            platform = platform.toLowerCase().trim();
            System.out.println("[Driver] thread=" + Thread.currentThread().getName() + " selectedPlatform=" + platform);

            String appPath = System.getProperty("user.dir") + "/" + ConfigReader.getProperty(platform + ".app.path");

            try {
                String serverUrlFromConfig = ConfigReader.getProperty("appium.server.url");
                URL serverUrl = new URL(serverUrlFromConfig);

                switch (platform) {
                    case "android":
                        UiAutomator2Options androidOptions = new UiAutomator2Options()
                                .setDeviceName(ConfigReader.getProperty("android.device.name"))
                                .setApp(appPath)
                                .setAutomationName("UiAutomator2")
                                .setNewCommandTimeout(Duration.ofSeconds(60000));
                        driverPool.set(new AndroidDriver(serverUrl, androidOptions));
                        break;

                    case "ios":
                        XCUITestOptions iosOptions = new XCUITestOptions()
                                .setDeviceName(ConfigReader.getProperty("ios.device.name"))
                                .setApp(appPath)
                                .setAutomationName("XCUITest")
                                .setNewCommandTimeout(Duration.ofSeconds(60000));
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