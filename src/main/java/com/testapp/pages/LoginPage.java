package com.testapp.pages;

import com.testapp.utils.Driver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;

public class LoginPage {

    public LoginPage() {
        // We initialize the elements with a 10-second implicit wait for stability
        PageFactory.initElements(new AppiumFieldDecorator(Driver.getDriver(), Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(accessibility = "input-engineer-id")
    @iOSXCUITFindBy(accessibility = "input-engineer-id")
    private WebElement engineerIdInput;

    @AndroidFindBy(accessibility = "input-security-token")
    @iOSXCUITFindBy(accessibility = "input-security-token")
    private WebElement securityTokenInput;

    @AndroidFindBy(accessibility = "btn-login")
    @iOSXCUITFindBy(accessibility = "btn-login")
    private WebElement loginButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"error-message\")")
    @iOSXCUITFindBy(accessibility = "error-message")
    private WebElement errorText;

    /**
     * Performs login action
     * @param id Engineer ID
     * @param token Security token
     */
    public void login(String id, String token) {
        engineerIdInput.sendKeys(id);
        securityTokenInput.sendKeys(token);
        loginButton.click();
    }

    /**
     *
     */
    public String getErrorMessage() {
        return errorText.getText();
    }

}