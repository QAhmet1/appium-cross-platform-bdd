package com.testapp.pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
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
     * Optional: Generic method to check visibility (for assertions).
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}