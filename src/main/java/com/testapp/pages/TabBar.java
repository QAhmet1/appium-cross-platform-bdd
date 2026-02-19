package com.testapp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class TabBar extends BasePage {

    public TabBar() {
        super(); // Calls BasePage constructor to initialize elements and wait
    }

    @AndroidFindBy(accessibility = "tab-dash")
    @iOSXCUITFindBy(accessibility = "tab-dash")
    private WebElement tabDash;

    @AndroidFindBy(accessibility = "tab-assets")
    @iOSXCUITFindBy(accessibility = "tab-assets")
    private WebElement tabAssets;

    @AndroidFindBy(accessibility = "tab-types")
    @iOSXCUITFindBy(accessibility = "tab-types")
    private WebElement tabTypes;

    @AndroidFindBy(accessibility = "tab-lab")
    @iOSXCUITFindBy(accessibility = "tab-lab")
    private WebElement tabLab;

    @AndroidFindBy(accessibility = "tab-profile")
    @iOSXCUITFindBy(accessibility = "tab-profile")
    private WebElement tabProfile;

    // Methods using BasePage's verifyAndClick
    public void clickDash() { verifyAndClick(tabDash); }
    public void clickAssets() { verifyAndClick(tabAssets); }
    public void clickTypes() { verifyAndClick(tabTypes); }
    public void clickLab() { verifyAndClick(tabLab); }
    public void clickProfile() { verifyAndClick(tabProfile); }

    public boolean isDashTabVisible() {
        /*
         * Checks if the Dashboard tab is visible to verify successful login/navigation.
         * Uses the inherited wait from BasePage.
         */
        try {
            return isDisplayed(tabDash);
        } catch (Exception e) {
            return false;
        }
    }
}