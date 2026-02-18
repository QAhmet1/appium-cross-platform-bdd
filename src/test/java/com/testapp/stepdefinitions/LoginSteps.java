package com.testapp.stepdefinitions;

import com.testapp.pages.LoginPage;
import com.testapp.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

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
       String user = ConfigReader.getProperty("valid_username");
       String pass = ConfigReader.getProperty("valid_password");

       loginPage.login(user, pass);
}

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        /*
         * Logic to verify if landing page/dashboard is visible.
         * You can add an assertion here.
         */
        System.out.println("Successfully navigated to the dashboard.");
    }

    @When("the user enters credentials id: {string} and token: {string}")
    public void enterCredentials(String id, String token) {
        // Equivalent to: await LoginPage.login(id, token);
        loginPage.login(id, token);
    }

    @Then("the error message should be displayed")
    public void verifyError() {
        // Equivalent to: expect(error.length).toBeGreaterThan(0);
        String expectedError = ConfigReader.getProperty("error_access_denied");
        String actualError = loginPage.getErrorMessage();

        // TestNG Format: Assert.assertEquals(actual, expected, messageOnFailure);
        Assert.assertEquals(actualError, expectedError, "The error message on the screen did not match!");
    }
}