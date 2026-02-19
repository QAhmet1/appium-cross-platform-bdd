package com.testapp.stepdefinitions;

import com.testapp.pages.LoginPage;
import com.testapp.pages.ProfilePage;
import com.testapp.pages.TabBar;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ProfileSteps {

    ProfilePage profilePage = new ProfilePage();
    TabBar tabBar = new TabBar();
    LoginPage loginPage = new LoginPage();

    @When("the user navigates to the Profile tab")
    public void navigateToProfile() {
        tabBar.clickProfile();
    }

    @Then("the profile avatar should be visible")
    public void verifyAvatar() {
        Assert.assertTrue(profilePage.isAvatarDisplayed(), "Profile avatar is not visible!");
    }

    @And("the terminate session button should be visible")
    public void verifyTerminateBtn() {
        Assert.assertTrue(profilePage.isTerminateSessionBtnDisplayed(), "Terminate Session button is not visible!");
    }

    @When("the user logs out from the profile screen")
    public void logout() {
        profilePage.clickLogout();
    }

    @Then("the user should see the login screen")
    public void verifyLoginScreen() {
        /*
         * Verifies logout success by checking the login-footer on the login screen.
         */
        Assert.assertTrue(loginPage.isLoginScreenVisible(), "Login screen footer is not visible after logout!");
    }
}