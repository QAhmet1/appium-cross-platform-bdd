package com.testapp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ProfilePage extends BasePage {

    public ProfilePage() {
        super();
    }

    @AndroidFindBy(accessibility = "profile-avatar")
    @iOSXCUITFindBy(accessibility = "profile-avatar")
    private WebElement profileAvatar;

    @AndroidFindBy(accessibility = "btn-logout")
    @iOSXCUITFindBy(accessibility = "btn-logout")
    private WebElement terminateSessionBtn;

    /**
     * Verifies if the profile avatar is displayed on the screen.
     */
    public boolean isAvatarDisplayed() {
        return isDisplayed(profileAvatar);
    }

    /**
     * Verifies if the terminate session button is displayed.
     */
    public boolean isTerminateSessionBtnDisplayed() {
        return isDisplayed(terminateSessionBtn);

        /*
         * Performs logout by clicking the terminate session button.
         */
    }
    public void clickLogout() {
        verifyAndClick(terminateSessionBtn);
        handleIosAlert();
    }
}