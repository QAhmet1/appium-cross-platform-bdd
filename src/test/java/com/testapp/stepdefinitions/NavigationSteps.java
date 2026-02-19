// Inside NavigationSteps.java
package com.testapp.stepdefinitions;

import com.testapp.pages.TabBar;
import io.cucumber.java.en.Then;

public class NavigationSteps {

    TabBar tabBar = new TabBar();

    @Then("the user clicks on {string} tab")
    public void user_clicks_tab(String tabName) {
        /**
         * The click method internally uses verifyAndClick from BasePage
         * ensuring the element is visible and interactable.
         */
        switch (tabName.toLowerCase()) {
            case "dash":    tabBar.clickDash(); break;
            case "assets":  tabBar.clickAssets(); break;
            case "types":   tabBar.clickTypes(); break;
            case "lab":     tabBar.clickLab(); break;
            case "profile": tabBar.clickProfile(); break;
            default: throw new IllegalArgumentException("Invalid tab: " + tabName);
        }
    }
}