package com.BookIt.step_definitions;

import com.BookIt.pages.LoginPage;
import com.BookIt.utilities.BrowserUtils;
import com.BookIt.utilities.ConfigurationReader;
import com.BookIt.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class Login_StepDefinitions {

    LoginPage loginPage = new LoginPage();

    @Given("User should be on the login page")
    public void user_should_be_on_the_login_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

    }




    @When("User inputs {string} , {string} and clicks on sign in")
    public void user_inputs_and_clicks_on_sign_in(String username, String password) {
       loginPage.inputUserName.sendKeys(username);
       loginPage.inputPassword.sendKeys(password);
       loginPage.submitButton.click();

    }
    @Then("User should be on the map page")
    public void user_should_be_on_the_map_page() {


        BrowserUtils.waitUntilURLContains("map");

        String actualUrl = Driver.getDriver().getCurrentUrl();
        String expectedResult = "https://cybertek-reservation-qa.herokuapp.com/map";

        Assert.assertTrue(actualUrl.equals(expectedResult));


    }


}
