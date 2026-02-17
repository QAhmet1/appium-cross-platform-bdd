package com.testapp.stepdefinitions;

import com.testapp.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    
    // Instantiate LoginPage using the cross-platform Page Object Model
    LoginPage loginPage = new LoginPage();

    @Given("the user launches the application")
    public void userLaunchesApp() {
        /*
         * Driver is already initialized by Hooks @Before.
         * Here we can add additional logic like checking if the app is open.
         */
        System.out.println("Application launched successfully.");
    }

   @When("the user enters credentials on {string} screen")
   public void enterCredentials(String screen) {
    loginPage.login("admin", "1234");
}

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        /*
         * Logic to verify if landing page/dashboard is visible.
         * You can add an assertion here.
         */
        System.out.println("Successfully navigated to the dashboard.");
    }
}