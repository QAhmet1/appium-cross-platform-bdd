package com.testapp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.testapp.utils.Driver;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriverWait wait;

    public BasePage() {
        /*
         * Initialize elements for all child pages and setup common wait.
         */
        PageFactory.initElements(new AppiumFieldDecorator(Driver.getDriver()), this);
        this.wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    }


    @AndroidFindBy(accessibility = "OK")
    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement globalOkButton;

    /**
     * Handles the session modal specifically for iOS.
     * Since Android doesn't show this popup, it will catch the exception
     * and continue the test immediately.
     */
    public void handleIosAlert() {
        try {
            WebDriverWait shortWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.visibilityOf(globalOkButton)).click();
            System.out.println("iOS Alert 'OK' clicked successfully.");
        } catch (Exception e) {
            System.out.println("No iOS alert detected, moving forward.");
        }
    }

    /**
     * Generic method to verify visibility and clickability before interaction.
     * @param element The target WebElement
     */
    protected void verifyAndClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    /**
     * Checks if a web element is displayed using the standard explicit wait.
     * @param element the WebElement to check
     * @return true if visible, false if not
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            // Standart wait süremizi kullanıyoruz (örn: 10 saniye)
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptAlertIfPresent() {
        /*
         * Attempts to accept a system alert if it appears.
         * Especially needed for iOS logout confirmations or session termination popups.
         */
        try {
            Driver.getDriver().switchTo().alert().accept();
        } catch (Exception e) {
            // No alert was present, continue safely
        }
    }
}