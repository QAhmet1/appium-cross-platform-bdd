package com.testapp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage{

    public LoginPage() {
        // We initialize the elements with a 10-second implicit wait for stability
//        PageFactory.initElements(new AppiumFieldDecorator(Driver.getDriver(), Duration.ofSeconds(10)), this);
        super();
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

    @AndroidFindBy(accessibility = "login-screen")
    @iOSXCUITFindBy(accessibility = "login-screen")
    private WebElement loginScreen;

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

    /**
     * Verifies if the login screen is displayed by checking the footer element.
     * @return boolean indicating the visibility of the login footer.
     */
    public boolean isLoginScreenVisible() {
        /*
         * We use the footer as a stable anchor point to confirm
         * the user is back on the login screen.
         */
        return isDisplayed(loginScreen);
    }

}